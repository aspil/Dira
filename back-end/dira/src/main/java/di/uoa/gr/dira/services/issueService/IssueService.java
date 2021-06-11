package di.uoa.gr.dira.services.issueService;

import com.sun.istack.Nullable;
import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.issue.IssueLabel;
import di.uoa.gr.dira.entities.project.Permission;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.issue.IssueNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.models.issue.IssueRequestModel;
import di.uoa.gr.dira.models.issue.IssueResponseModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.IssueLabelRepository;
import di.uoa.gr.dira.repositories.IssueRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.shared.PermissionType;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class IssueService extends BaseService<IssueRequestModel, Issue, Long, IssueRepository> implements IIssueService {
    ProjectRepository projectRepository;
    CustomerRepository customerRepository;
    IssueLabelRepository issueLabelRepository;
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

    @Nullable
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

    public IssueResponseModel createIssueWithProjectId(Long projectId, Long customerId, IssueRequestModel issueRequestModel) {
        Customer reporter = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        Permission permissionType = project.getPermissions()
                .stream()
                .filter(permission -> permission.getUser().getId().equals(customerId) && PermissionType.hasWritePermissions(permission.getPermission()))
                .findFirst()
                .orElseThrow(ActionNotPermittedException::new);

        Issue newIssue = mapper.map(issueRequestModel, Issue.class);
        /* Start populating the new issue entity */
        newIssue.setReporter(reporter);
        newIssue.setCreated(new Date());
        newIssue.setUpdated(newIssue.getCreated());
        newIssue.setLabels(new ArrayList<>());

        List<IssueLabel> issueLabelList = newIssue.getLabels();
        if (issueLabelList != null) {
            for (String label : issueRequestModel.getLabels()) {
                IssueLabel issueLabel = issueLabelRepository.findByName(label).orElse(null);
                if (issueLabel == null) {
                    issueLabel = issueLabelRepository.save(new IssueLabel(label));
                }
                issueLabelList.add(issueLabel);
            }
        }
//        if (newIssue.getEpic() != null) {
//            Long epicId = newIssue.getEpic().getId();
//            Issue epicIssue = repository.findById(newIssue.getEpic().getId()).orElseThrow(() -> new IssueNotFoundException("issueId", epicId.toString()));
//            epicIssue.getIssueLinks().add(mapper.map(epicIssue, IssueLink.class));
//            repository.save(epicIssue);
//        }
        newIssue.setProject(project);
        newIssue = repository.save(newIssue);
        addIssueToProject(project, newIssue);
        return mapper.map(newIssue, IssueResponseModel.class);
    }

    @Override
    public IssueResponseModel findIssueWithProjectId(Long projectId, Long customerId, Long issueId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        project.getPermissions()
                .stream()
                .filter(permission -> permission.getUser().getId().equals(customerId) && PermissionType.hasReadPermissions(permission.getPermission()))
                .findFirst()
                .orElseThrow(ActionNotPermittedException::new);

        Issue issue = project.getIssues()
                .stream()
                .filter(obj -> obj.getId().equals(issueId))
                .findAny()
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));

        return mapper.map(issue, IssueResponseModel.class);
    }

    @Override
    public IssueResponseModel updateIssueWithProjectId(Long projectId, Long customerId, Long issueId, IssueRequestModel issueRequestModel) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        project.getPermissions()
                .stream()
                .filter(permission -> permission.getUser().getId().equals(customerId) && PermissionType.hasWritePermissions(permission.getPermission()))
                .findFirst()
                .orElseThrow(ActionNotPermittedException::new);
        Issue updatedIssue = mapper.map(issueRequestModel, Issue.class);
        updatedIssue.setUpdated(new Date());
        updatedIssue = repository.save(updatedIssue);

        addIssueToProject(project, updatedIssue);

        return mapper.map(updatedIssue, IssueResponseModel.class);
    }

    @Override
    public void deleteIssueWithProjectId(Long projectId, Long customerId, Long issueId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        project.getPermissions()
                .stream()
                .filter(permission -> permission.getUser().getId().equals(customerId) && PermissionType.hasDeletePermissions(permission.getPermission()))
                .findFirst()
                .orElseThrow(ActionNotPermittedException::new);

        List<Issue> projectIssues = project.getIssues();
        Issue issue = projectIssues.stream()
                .filter(obj -> obj.getId().equals(issueId))
                .findAny()
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));

        projectIssues.remove(issue);

        repository.delete(issue);
        projectRepository.save(project);
    }}
