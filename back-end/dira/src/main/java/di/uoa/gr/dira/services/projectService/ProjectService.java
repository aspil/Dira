package di.uoa.gr.dira.services.projectService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.project.Permission;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectAlreadyExistsException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.PermissionRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.shared.PermissionType;
import di.uoa.gr.dira.shared.ProjectVisibility;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService extends BaseService<ProjectModel, Project, Long, ProjectRepository> implements IProjectService {
    CustomerRepository customerRepository;
    PermissionRepository permissionRepository;

    public ProjectService(ProjectRepository repository, CustomerRepository customerRepository, PermissionRepository permissionRepository, ModelMapper mapper) {
        super(repository, mapper);
        this.customerRepository = customerRepository;
        this.permissionRepository = permissionRepository;
    }

    private Project checkPermissions(Long projectId, Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));
        Project project = repository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        project.getPermissions()
                .stream()
                .filter(permission -> permission.getUser().getId().equals(customerId) && PermissionType.ADMIN.hasPermission(permission.getPermission()))
                .findFirst()
                .orElseThrow(ActionNotPermittedException::new);

        return project;
    }

    @Override
    public List<ProjectModel> findAllPublicProjects() {
        List<Project> publicProjects = repository.findAll()
                .stream()
                .filter(project -> project.getVisibility() == ProjectVisibility.PUBLIC)
                .collect(Collectors.toList());

        return MapperHelper.mapList(mapper, publicProjects, modelType);
    }

    @Override
    public ProjectModel createProject(Long customerId, ProjectModel projectModel) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));
        Optional<Project> projectExists = repository.findByKey(projectModel.getKey());
        if (projectExists.isPresent()) {
            throw new ProjectAlreadyExistsException("projectId", projectExists.get().getId().toString());
        }
        Project project = mapper.map(projectModel, entityType);
        if ((customer.getSubscriptionPlan().getPlan().equals(SubscriptionPlanEnum.STANDARD)) && (project.getVisibility().equals(ProjectVisibility.PRIVATE))) {
            throw new ActionNotPermittedException();
        }
        // setting permissions when creating a project
        project.setPermissions(new ArrayList<>());

        // adding customer who created the project
        project.setCustomers(new ArrayList<>());
        project.getCustomers().add(customer);

        project.setIssues(new ArrayList<>());

        /* Create a new permission for this customer in the current project */
        Permission permission = new Permission();
        permission.setPermission(PermissionType.ADMIN.getPermission());
        permission.setUser(customer);

        permission = permissionRepository.save(permission);

        project.getPermissions().add(permission);
        project = repository.save(project); // TODO: maybe there is a way to avoid double save. This is a workaround due to circular dependency!

        return mapper.map(project, ProjectModel.class);
    }


    @Override
    public ProjectModel getProject(Long projectId, SubscriptionPlanEnum subscriptionPlan) {
        Project project = repository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
        if (project.getVisibility().equals(ProjectVisibility.PRIVATE) && subscriptionPlan.equals(SubscriptionPlanEnum.STANDARD)) {
            throw new ActionNotPermittedException();
        }

        return mapper.map(project, modelType);
    }


    @Override
    public ProjectModel updateProjectWithId(Long projectId, Long customerId, ProjectModel projectModel) {
        checkPermissions(projectId, customerId);
        return super.save(projectModel);
    }

    @Override
    public void deleteProjectWithId(Long projectId, Long customerId) {
        Project project = checkPermissions(projectId, customerId);
        List<Customer> customers = project.getCustomers();
        for (int i = 0; i != customers.size(); ++i) {
            customers.get(i).getProjects().remove(project);
        }
        customerRepository.saveAll(customers);
        repository.deleteById(projectId);
    }

    /* ProjectUserController */

    @Override
    public ProjectUsersModel findUsersByProjectId(Long id) {
        return repository.findById(id)
                .map(project -> mapper.map(project, ProjectUsersModel.class))
                .orElseThrow(() -> new ProjectNotFoundException("projectId", id.toString()));
    }

    @Override
    public void addUserToProjectWithId(Long projectId, Long inviterId, Long inviteeId) {
        Project project = checkPermissions(projectId, inviterId);
        Customer customer = customerRepository.findById(inviteeId).orElseThrow(() -> new CustomerNotFoundException("userId", inviteeId.toString()));
        Permission permission = new Permission();
        permission.setUser(customer);
        permission.setPermission(PermissionType.READ.getPermission());
        permission = permissionRepository.save(permission);
        project.getPermissions().add(permission);
        project.getCustomers().add(customer);
        repository.save(project);
    }


    @Override
    public void deleteUserFromProjectWithId(Long id, Long projectOwnerId, Long userId) {
        customerRepository.findById(userId).orElseThrow(() -> new CustomerNotFoundException("userId", userId.toString()));
        Project project = checkPermissions(id, projectOwnerId);

        project.getPermissions().removeIf(permission -> permission.getUser().getId().equals(userId));

        List<Customer> customers = project.getCustomers();
        Customer customer = customers.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("userId", userId.toString()));
        customers.remove(customer);
        // we might need to delete the project from customer's project list
        repository.save(project);
    }

    @Override
    public void deleteUserFromAllProjects(Long userId) {
        Customer customer = customerRepository.findById(userId).orElseThrow(() -> new CustomerNotFoundException("userId", userId.toString()));

        customer.getProjects().forEach(project -> {
            project.getCustomers().remove(customer);
            project.getPermissions().removeIf(permission -> permission.getUser().getId().equals(customer.getId()));
        });
        customerRepository.save(customer);
    }
}