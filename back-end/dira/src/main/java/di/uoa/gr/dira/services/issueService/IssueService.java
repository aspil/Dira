package di.uoa.gr.dira.services.issueService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.issue.IssueNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.models.issue.IssueCreateModel;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.IssueLabelRepository;
import di.uoa.gr.dira.repositories.IssueRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.shared.IssueStatusEnum;
import di.uoa.gr.dira.shared.PermissionType;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IssueService extends BaseService<IssueModel, Issue, Long, IssueRepository> implements IIssueService {
    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final IssueLabelRepository issueLabelRepository;
    Logger logger = Logger.getLogger(IssueService.class);

    public IssueService(IssueRepository repository,
                 ProjectRepository projectRepository,
                 CustomerRepository customerRepository,
                 IssueLabelRepository issueLabelRepository,
                 ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
        this.issueLabelRepository = issueLabelRepository;
    }

    private void addIssueToProject(Project project, Issue issue) {
        List<Issue> projectIssues = project.getIssues();
        if (projectIssues != null) {
            int issueIdx = projectIssues.indexOf(issue);
            if (issueIdx != -1) {
                projectIssues.set(issueIdx, issue);   /* If issue exists, set instead of re adding to list */
            } else {
                projectIssues.add(issue);
            }
            projectRepository.save(project);
        }
    }

    @Override
    public ProjectIssuesModel findAllIssuesByProjectId(Long projectId) { // TODO filter by issue status(probably)
        return projectRepository.findById(projectId)
                .map(project -> mapper.map(project, ProjectIssuesModel.class))
//                .filter()
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
    }

    public IssueModel createIssueWithProjectId(Long projectId, Long customerId, IssueCreateModel issueModel) {
        Customer reporter = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        checkProjectUserPermissions(customerId, project, PermissionType.WRITE);

        Issue newIssue = mapper.map(issueModel, Issue.class);

        /* Start populating the new issue entity */
        newIssue.setProject(project);
        newIssue.setReporter(reporter);
        newIssue.setCreated(new Date());
        newIssue.setUpdated(newIssue.getCreated());
        newIssue.setStatus(IssueStatusEnum.New);

        if (issueModel.getEpicId() != null) {
            Issue epic = repository.findById(issueModel.getEpicId())
                    .orElseThrow(() -> new IssueNotFoundException(String.format("Epic with id %s not found", issueModel.getEpicId())));

            newIssue.setEpic(epic);
        }

        if (issueModel.getAssigneeId() != null) {
            Customer assignee = customerRepository.findById(issueModel.getAssigneeId())
                    .orElseThrow(() -> new CustomerNotFoundException("id", issueModel.getAssigneeId().toString()));

            newIssue.setAssignee(assignee);
        }

        newIssue.setKey(String.format("%s-%d", project.getKey(), project.getIssues().size()));

//        List<IssueLabel> issueLabelList = newIssue.getLabels();
//        if (issueLabelList != null) {
//            for (String label : issueRequestModel.getLabels()) {
//                IssueLabel issueLabel = issueLabelRepository.findByName(label).orElse(null);
//                if (issueLabel == null) {
//                    issueLabel = issueLabelRepository.save(new IssueLabel(label));
//                }
//                issueLabelList.add(issueLabel);
//            }
//        }

        newIssue = repository.save(newIssue);
        return mapper.map(newIssue, IssueModel.class);
    }

    @Override
    public IssueModel findIssueWithProjectId(Long projectId, Long customerId, Long issueId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        checkProjectUserPermissions(customerId, project, PermissionType.READ);

        Issue issue = project.getIssues()
                .stream()
                .filter(obj -> obj.getId().equals(issueId))
                .findFirst()
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));

        return mapper.map(issue, IssueModel.class);
    }

//    @Override
//    public IssueModel updateIssueWithProjectId(Long projectId, Long customerId, Long issueId, IssueRequestModel issueRequestModel) {
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
//
//        checkProjectUserPermissions(customerId, project, PermissionType.WRITE);
//
//        Issue updatedIssue = mapper.map(issueRequestModel, Issue.class);
//        updatedIssue.setUpdated(new Date());
//        updatedIssue = repository.save(updatedIssue);
//
//        addIssueToProject(project, updatedIssue);
//
//        return mapper.map(updatedIssue, IssueModel.class);
//    }

    @Override
    public void deleteIssueWithProjectId(Long projectId, Long customerId, Long issueId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        checkProjectUserPermissions(customerId, project, PermissionType.DELETE);

        List<Issue> projectIssues = project.getIssues();
        Issue issue = projectIssues.stream()
                .filter(obj -> obj.getId().equals(issueId))
                .findAny()
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));

        projectIssues.remove(issue);

        repository.delete(issue);
        projectRepository.save(project);
    }

    private void checkProjectUserPermissions(long customerId, Project project, PermissionType requiredPermission) {
        project.getPermissions()
                .stream()
                .filter(permission -> permission.getUser().getId().equals(customerId) && requiredPermission.hasPermission(permission.getPermission()))
                .findFirst()
                .orElseThrow(ActionNotPermittedException::new);
    }
}
