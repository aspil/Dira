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
import di.uoa.gr.dira.services.permissionService.IPermissionService;
import di.uoa.gr.dira.shared.IssueStatusEnum;
import di.uoa.gr.dira.shared.PermissionType;
import org.hibernate.secure.spi.PermissionCheckEntityInformation;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.text.AttributedCharacterIterator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService extends BaseService<IssueModel, Issue, Long, IssueRepository> implements IIssueService {
    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final IPermissionService permissionService;

    public IssueService(IssueRepository repository,
                 ProjectRepository projectRepository,
                 CustomerRepository customerRepository,
                 IPermissionService permissionService,
                 ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
        this.permissionService = permissionService;
    }

    @Override
    public ProjectIssuesModel findAllIssuesByProjectId(Long projectId) { // TODO filter by issue status(probably)
        return projectRepository.findById(projectId)
                .map(project -> mapper.map(project, ProjectIssuesModel.class))
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
    }

    public IssueModel createIssueWithProjectId(Long projectId, Long customerId, IssueCreateModel issueModel) {
        Customer reporter = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        if (!permissionService.checkProjectUserPermissions(customerId, project, PermissionType.WRITE)) {
            throw new ActionNotPermittedException("You need WRITE permissions in order to create an issue");
        }

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

        newIssue = repository.save(newIssue);
        return mapper.map(newIssue, IssueModel.class);
    }

    @Override
    public Optional<IssueModel> findIssueWithProjectId(Long projectId, Long customerId, Long issueId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        if (!permissionService.checkProjectUserPermissions(customerId, project, PermissionType.READ)) {
            throw new ActionNotPermittedException("You need READ permissions in order to view an issue");
        }

        return project.getIssues()
                .stream()
                .filter(obj -> obj.getId().equals(issueId))
                .findFirst()
                .map(issue -> mapper.map(issue, modelType));
    }

    @Override
    public IssueModel updateIssueWithProjectId(Long projectId, Long customerId, Long issueId, IssueModel issueModel) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        if (!permissionService.checkProjectUserPermissions(customerId, project, PermissionType.WRITE)) {
            throw new ActionNotPermittedException("You need WRITE permissions in order to update an issue");
        }

        Issue issue = repository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));

        mapper.map(issue, issueModel);
        issue.setUpdated(new Date());
        issue = repository.save(issue);

        return mapper.map(issue, IssueModel.class);
    }

    @Override
    public void deleteIssueWithProjectId(Long projectId, Long customerId, Long issueId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        if (!permissionService.checkProjectUserPermissions(customerId, project, PermissionType.DELETE)) {
            throw new ActionNotPermittedException("You need DELETE permissions in order to delete an issue");
        }

        List<Issue> projectIssues = project.getIssues();
        Issue issue = projectIssues.stream()
                .filter(obj -> obj.getId().equals(issueId))
                .findAny()
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));

        projectIssues.remove(issue);

        repository.delete(issue);
        projectRepository.save(project);
    }
}
