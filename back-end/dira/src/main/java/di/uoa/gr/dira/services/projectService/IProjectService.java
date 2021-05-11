package di.uoa.gr.dira.services.projectService;

import di.uoa.gr.dira.controllers.ProjectUserPermissionController;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.models.project.ProjectUserPermissionModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.services.IService;

import java.util.List;

public interface IProjectService extends IService<ProjectModel, Long> {
    ProjectUsersModel findUsersByProjectId(Long id);
    void addUserToProjectWithId(Long id, Long userId);
    void deleteUserFromProjectWithId(Long id, Long userId);
    ProjectModel updateProjectWithId(ProjectModel projectMode);
}
