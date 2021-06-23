package di.uoa.gr.dira.services.permissionService;

import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.project.permission.ProjectUserPermissionModel;
import di.uoa.gr.dira.services.IService;
import di.uoa.gr.dira.shared.PermissionType;

import java.util.List;
import java.util.Optional;

public interface IPermissionService extends IService<ProjectUserPermissionModel, Long> {
    List<ProjectUserPermissionModel> getAllProjectUserPermissions(Long projectId);

    Optional<ProjectUserPermissionModel> getProjectUserPermissionById(Long customerId, Long projectId, Long permissionId);

    ProjectUserPermissionModel createProjectUserPermission(Long creatorId, Long projectId, ProjectUserPermissionModel userPermissionModel);

    ProjectUserPermissionModel updateProjectUserPermission(Long customerId, Long projectId, Long permissionId, ProjectUserPermissionModel userPermissionModel);

    void deleteProjectUserPermission(Long customerId, Long projectId, Long permissionId);

    boolean checkProjectUserPermissions(Long customerId, Project project, PermissionType requiredPermission);
}
