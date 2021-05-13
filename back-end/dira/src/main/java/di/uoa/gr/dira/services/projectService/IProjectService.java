package di.uoa.gr.dira.services.projectService;

import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.services.IService;

public interface IProjectService extends IService<ProjectModel, Long> {
    ProjectModel createProject(Long customerId, ProjectModel projectModel);

    ProjectUsersModel findUsersByProjectId(Long id);
    void addUserToProjectWithId(Long id, Long userId);
    void deleteUserFromProjectWithId(Long id, Long userId);
    ProjectModel updateProjectWithId(ProjectModel projectMode);
}
