package di.uoa.gr.dira.services.projectService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.shared.ProjectVisibility;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService extends BaseService<ProjectModel, Project, Long, ProjectRepository> implements IProjectService {
    CustomerRepository customerRepository;

    ProjectService(ProjectRepository repository, CustomerRepository customerRepository, ModelMapper mapper) {
        super(repository, mapper);
        this.customerRepository = customerRepository;
    }


    /* ProjectController */

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
        Project project = mapper.map(projectModel, entityType);
        if ((customer.getSubscriptionPlan().getPlan() == SubscriptionPlanEnum.STANDARD) && (project.getVisibility().equals(ProjectVisibility.PRIVATE))) {
            throw new ActionNotPermittedException();
        }
        project.setCustomers(new ArrayList<>());
        project.getCustomers().add(customer);

        return mapper.map(repository.save(project), modelType);
    }


    @Override
    public ProjectModel getProject(Long projectId, SubscriptionPlanEnum subscriptionPlan) {
        Project project = repository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
        if (project.getVisibility().equals(ProjectVisibility.PRIVATE) && subscriptionPlan == SubscriptionPlanEnum.STANDARD) {
            throw new ActionNotPermittedException();
        }

        return mapper.map(project, modelType);
    }


    @Override
    public ProjectModel updateProjectWithId(Long projectId, ProjectModel projectModel) {
        Project project = repository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        return super.save(projectModel);
    }

    @Override
    public void deleteProjectWithId(Long projectId) {
        Project project = repository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
        List<Customer> customers = project.getCustomers();
        for (int i = 0; i != customers.size(); ++i) {
            customers.get(i).getProjects().remove(project);
            customerRepository.save(customers.get(i));
        }
        repository.deleteById(projectId);
    }

    /* ProjectUserController */

    @Override
    public ProjectUsersModel findUsersByProjectId(Long id) {
        return repository.findById(id).map(project -> mapper.map(project, ProjectUsersModel.class)).orElseThrow(() -> new ProjectNotFoundException("projectId", id.toString()));
    }

    @Override
    public void addUserToProjectWithId(Long id, Long userId) {
        Project project = repository.findById(id).orElseThrow(() -> new ProjectNotFoundException("projectId", id.toString()));
        Customer customer = customerRepository.findById(userId).orElseThrow(() -> new CustomerNotFoundException("userId", userId.toString()));
        project.getCustomers().add(customer);
        repository.save(project);
    }


    @Override
    public void deleteUserFromProjectWithId(Long id, Long userId) {
        boolean userFound = false;
        Project project = repository.findById(id).orElseThrow(() -> new ProjectNotFoundException("projectId", id.toString()));

        List<Customer> customers = project.getCustomers();
        if (!customers.isEmpty()) {
            for (int i = 0; i != customers.size(); ++i) {
                if (customers.get(i).getId().equals(userId)) {
                    userFound = true;
                    customers.remove(i);
                    repository.save(project);
                    break;
                }
            }
        }

        if (!userFound) {
            throw new CustomerNotFoundException("userId", userId.toString());
        }
    }
}