package di.uoa.gr.dira.services.permissionService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.models.project.permission.ProjectUserPermissionModel;
import di.uoa.gr.dira.services.IService;
import di.uoa.gr.dira.shared.PermissionType;

import java.util.List;

public interface IPermissionService extends IService<ProjectUserPermissionModel, Long> {
    List<ProjectUserPermissionModel> getAllProjectUserPermissions(Long projectId);

    ProjectUserPermissionModel createProjectUserPermission(Long creatorId, Long projectId, ProjectUserPermissionModel userPermissionModel);

    ProjectUserPermissionModel updateProjectUserPermission(Long customerId, Long projectId, Long permissionId, ProjectUserPermissionModel userPermissionModel);

    void deleteProjectUserPermission(Long customerId, Long projectId, Long permissionId);

    boolean checkProjectUserPermissions(Long customerId, Project project, PermissionType requiredPermission);
}
