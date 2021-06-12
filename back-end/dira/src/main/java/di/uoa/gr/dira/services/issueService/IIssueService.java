package di.uoa.gr.dira.services.issueService;

import di.uoa.gr.dira.models.issue.IssueCreateModel;
import di.uoa.gr.dira.models.issue.IssueRequestModel;
import di.uoa.gr.dira.models.issue.IssueCreateResponseModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.services.IService;

public interface IIssueService extends IService<IssueRequestModel, Long> {
    ProjectIssuesModel findAllIssuesByProjectId(Long id);

    IssueCreateResponseModel createIssueWithProjectId(Long projectId, Long customerId, IssueCreateModel issueModel);

    IssueCreateResponseModel findIssueWithProjectId(Long projectId, Long customerId, Long issueId);

    IssueCreateResponseModel updateIssueWithProjectId(Long projectId, Long customerId, Long issueId, IssueRequestModel issueModel);

    void deleteIssueWithProjectId(Long projectId, Long customerId, Long issueId);
}
