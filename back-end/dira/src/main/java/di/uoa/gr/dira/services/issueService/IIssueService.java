package di.uoa.gr.dira.services.issueService;

import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.services.IService;

public interface IIssueService extends IService<IssueModel, Long> {
    ProjectIssuesModel findAllIssuesByProjectId(Long id);
    IssueModel createIssueWithProjectId(Long projectId, IssueModel issueModel);
    IssueModel findIssueWithProjectId(Long projectId, Long issueId);
    IssueModel updateIssueWithProjectId(Long projectId, Long issueId, IssueModel issueModel);
    void deleteIssueWithProjectId(Long projectId, Long issueId);
}
