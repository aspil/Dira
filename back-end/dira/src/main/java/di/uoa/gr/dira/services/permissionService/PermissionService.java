package di.uoa.gr.dira.services.permissionService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.project.Permission;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.exceptions.project.permission.PermissionNotFoundException;
import di.uoa.gr.dira.models.project.permission.ProjectUserPermissionModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.PermissionRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.shared.PermissionType;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService extends BaseService<ProjectUserPermissionModel, Permission, Long, PermissionRepository> implements IPermissionService {
    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;

    public PermissionService(PermissionRepository repository,
                             ProjectRepository projectRepository,
                             CustomerRepository customerRepository,
                             ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean checkProjectUserPermissions(Long customerId, Project project, PermissionType requiredPermission) {
        return project.getPermissions()
                .stream()
                .anyMatch(permission -> permission.getUser().getId().equals(customerId) &&
                        requiredPermission.hasPermission(permission.getPermission()));
    }

    @Override
    public List<ProjectUserPermissionModel> getAllProjectUserPermissions(Long projectId) {
        return projectRepository.findById(projectId)
                .map(project -> MapperHelper.<Permission, ProjectUserPermissionModel>mapList(mapper, project.getPermissions(), ProjectUserPermissionModel.class))
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
    }

    @Override
    public ProjectUserPermissionModel createProjectUserPermission(
            Long creatorId,
            Long projectId,
            ProjectUserPermissionModel userPermissionModel) {
        customerRepository.findById(creatorId)
                .orElseThrow(() -> new CustomerNotFoundException("id", creatorId.toString()));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("id", projectId.toString()));

        if (!checkProjectUserPermissions(creatorId, project, PermissionType.ADMIN)) {
            throw new ActionNotPermittedException("You need ADMIN permissions in order to create a Project User Permission");
        }

        Customer customer = customerRepository.findById(userPermissionModel.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("id", userPermissionModel.getCustomerId().toString()));

        Permission perm = mapper.map(userPermissionModel, entityType);
        perm.setUser(customer);
        perm.setProject(project);
        perm = repository.save(perm);
        return mapper.map(perm, modelType);
    }

    @Override
    public ProjectUserPermissionModel updateProjectUserPermission(
            Long customerId,
            Long projectId,
            Long permissionId,
            ProjectUserPermissionModel userPermissionModel) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("id", customerId.toString()));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("id", projectId.toString()));

        if (!checkProjectUserPermissions(customerId, project, PermissionType.ADMIN)) {
            throw new ActionNotPermittedException("You need ADMIN permissions in order to update a Project User Permission");
        }

        Permission permission = project.getPermissions()
                .stream()
                .filter(p -> p.getId().equals(permissionId))
                .findFirst()
                .orElseThrow(() -> new PermissionNotFoundException("id", permissionId.toString()));

        if (!permission.getUser().getId().equals(userPermissionModel.getCustomerId())) {
            throw new ActionNotPermittedException("You cannot change the user of an existing permission");
        }

        ProjectUserPermissionModel res = userPermissionModel;
        PermissionType permissionType = PermissionType.fromPermissionSet(userPermissionModel.getPermissions());
        if (permission.getPermission() != permissionType.getPermission()) {
            permission.setPermission(permissionType.getPermission());
            permission = repository.save(permission);
            res = mapper.map(permission, modelType);
        }

        return res;
    }

    @Override
    public void deleteProjectUserPermission(Long customerId, Long projectId, Long permissionId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("id", customerId.toString()));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("id", projectId.toString()));

        if (!checkProjectUserPermissions(customerId, project, PermissionType.ADMIN)) {
            throw new ActionNotPermittedException("You need ADMIN permissions in order to delete a project user permission");
        }

        Permission permission = project.getPermissions()
                .stream()
                .filter(obj -> obj.getId().equals(permissionId))
                .findAny()
                .orElseThrow(() -> new PermissionNotFoundException("permissionId", permissionId.toString()));

        repository.delete(permission);
    }
}
