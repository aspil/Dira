package di.uoa.gr.dira.services.issueService;

import di.uoa.gr.dira.models.issue.IssueRequestModel;
import di.uoa.gr.dira.models.issue.IssueResponseModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.services.IService;

public interface IIssueService extends IService<IssueRequestModel, Long> {
    ProjectIssuesModel findAllIssuesByProjectId(Long id);
    IssueResponseModel createIssueWithProjectId(Long projectId, Long customerId, IssueRequestModel issueModel);
    IssueResponseModel findIssueWithProjectId(Long projectId, Long issueId);
    IssueResponseModel updateIssueWithProjectId(Long projectId, Long issueId, IssueRequestModel issueModel);
    void deleteIssueWithProjectId(Long projectId, Long issueId);
}
