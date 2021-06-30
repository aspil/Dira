package di.uoa.gr.dira.services.permissionService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.project.permission.ProjectUserPermissionModel;
import di.uoa.gr.dira.repositories.PermissionRepository;
import di.uoa.gr.dira.services.IService;
import di.uoa.gr.dira.shared.PermissionType;
import di.uoa.gr.dira.shared.PermissionTypeEnum;

import java.util.List;
import java.util.Optional;

public interface IPermissionService extends IService<ProjectUserPermissionModel, Long, PermissionRepository> {
    List<ProjectUserPermissionModel> getAllProjectUserPermissions(Long projectId);

    Optional<ProjectUserPermissionModel> getProjectUserPermissionById(Long customerId, Long projectId, Long permissionId);

    ProjectUserPermissionModel createProjectUserPermission(Long creatorId, Long projectId, ProjectUserPermissionModel userPermissionModel);

    void createProjectUserPermission(Long creatorId, Project project, Customer targetUser, PermissionTypeEnum... permissions);

    ProjectUserPermissionModel updateProjectUserPermission(Long customerId, Long projectId, Long permissionId, ProjectUserPermissionModel userPermissionModel);

    void deleteProjectUserPermission(Long customerId, Long projectId, Long permissionId);
}
