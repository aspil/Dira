package di.uoa.gr.dira.services.permissionService;

import di.uoa.gr.dira.models.project.ProjectUserPermissionModel;
import di.uoa.gr.dira.services.IService;

import java.util.List;

public interface IPermissionService extends IService<ProjectUserPermissionModel, Long> {
    List<ProjectUserPermissionModel> getProjectPermissionsForUsers(Long projectId);
    void createUserPermission(Long projectId, ProjectUserPermissionModel userPermissionModel);
    ProjectUserPermissionModel updateUserPermission(Long projectId, Long permissionId, ProjectUserPermissionModel userPermissionModel);
    void deleteUserPermission(Long projectId, Long permissionId);
}
