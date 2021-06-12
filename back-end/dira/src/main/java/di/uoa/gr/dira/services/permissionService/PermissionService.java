package di.uoa.gr.dira.services.permissionService;

import di.uoa.gr.dira.entities.project.Permission;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.exceptions.project.permission.PermissionNotFoundException;
import di.uoa.gr.dira.models.project.ProjectUserPermissionModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.PermissionRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.shared.PermissionType;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class PermissionService extends BaseService<ProjectUserPermissionModel, Permission, Long, PermissionRepository> implements IPermissionService {
    ProjectRepository projectRepository;
    CustomerRepository customerRepository;

    public PermissionService(PermissionRepository repository, ProjectRepository projectRepository,
                             CustomerRepository customerRepository, ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
    }

    private Project checkPermissions(Long projectId, Long customerId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
        customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));

        project.getPermissions()
                .stream()
                .filter(permission -> permission.getUser().getId().equals(customerId) && PermissionType.ADMIN.hasPermission(permission.getPermission()))
                .findFirst()
                .orElseThrow(ActionNotPermittedException::new);

        return project;
    }

    @Override
    public List<ProjectUserPermissionModel> getProjectPermissionsForUsers(Long projectId) {
        return projectRepository.findById(projectId).map(project -> MapperHelper.<Permission, ProjectUserPermissionModel>mapList(mapper, project.getPermissions(), ProjectUserPermissionModel.class))
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
    }

    @Override
    public void createUserPermission(Long projectId, Long customerId, ProjectUserPermissionModel userPermissionModel) {
        Project project = checkPermissions(projectId, customerId);
        ProjectUserPermissionModel saved = super.save(userPermissionModel);
        project.getPermissions().add(mapper.map(saved, Permission.class));
    }

    @Override
    public @Valid ProjectUserPermissionModel updateUserPermission(Long projectId,
                                                                  Long permissionId,
                                                                  Long customerId,
                                                                  ProjectUserPermissionModel userPermissionModel) {
        checkPermissions(projectId, customerId);
        return super.save(userPermissionModel);
    }

    @Override
    public void deleteUserPermission(Long projectId, Long customerId, Long permissionId) {
        Project project = checkPermissions(projectId, customerId);
        Permission permission = project.getPermissions()
                .stream()
                .filter(obj -> obj.getId().equals(permissionId))
                .findAny()
                .orElseThrow(() -> new PermissionNotFoundException("permissionId", permissionId.toString()));

        project.getPermissions().remove(permission);
        projectRepository.save(project);
        repository.delete(permission);
    }
}
