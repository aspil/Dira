package di.uoa.gr.dira.services.issueService;

import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssueModel;
import di.uoa.gr.dira.services.IService;

public interface IIssueService extends IService<IssueModel, Long> {
    ProjectIssueModel findAllIssuesByProjectId(Long id);
    ProjectIssueModel createIssueToProject(Long projectId, IssueModel issueModel);
}
