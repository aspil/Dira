package di.uoa.gr.dira.services.issueService;

import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.services.IService;

public interface IIssueService extends IService<IssueModel, Long> {
    ProjectIssuesModel findAllIssuesByProjectId(Long id);
    void createIssueWithProjectId(Long projectId, IssueModel issueModel);
    IssueModel findIssueWithProjectId(Long issueId);
}
