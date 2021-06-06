package di.uoa.gr.dira.services.projectService;

import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.services.IService;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;

import java.util.List;

public interface IProjectService extends IService<ProjectModel, Long> {
    List<ProjectModel> findAllPublicProjects();

    ProjectModel createProject(Long customerId, ProjectModel projectModel);

    ProjectUsersModel findUsersByProjectId(Long projectId);

    void addUserToProjectWithId(Long projectId, Long userId);

    void deleteProjectWithId(Long projectId, Long customerId);

    void deleteUserFromProjectWithId(Long projectId, Long userId);

    void deleteUserFromAllProjects(Long userId);

    ProjectModel updateProjectWithId(Long projectId, Long customerId, ProjectModel projectModel);

    ProjectModel getProject(Long projectId, SubscriptionPlanEnum subscriptionPlan);
}
