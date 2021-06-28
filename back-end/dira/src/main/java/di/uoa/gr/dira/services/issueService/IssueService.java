package di.uoa.gr.dira.services.issueService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.issue.*;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.issue.IssueNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.models.issue.IssueCreateModel;
import di.uoa.gr.dira.models.issue.IssueLinkModel;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.repositories.*;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.services.permissionService.IPermissionService;
import di.uoa.gr.dira.shared.IssueLinkTypeEnum;
import di.uoa.gr.dira.shared.IssueStatusEnum;
import di.uoa.gr.dira.shared.PermissionType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IssueService extends BaseService<IssueModel, Issue, Long, IssueRepository> implements IIssueService {
    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final IssueFixVersionRepository fixVersionRepository;
    private final IssueLabelRepository labelRepository;
    private final IssueCommentRepository commentRepository;
    private final IPermissionService permissionService;

    public IssueService(IssueRepository repository,
                 ProjectRepository projectRepository,
                 CustomerRepository customerRepository,
                 IssueFixVersionRepository fixVersionRepository,
                 IssueLabelRepository labelRepository,
                 IssueCommentRepository commentRepository,
                 IPermissionService permissionService,
                 ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
        this.fixVersionRepository = fixVersionRepository;
        this.labelRepository = labelRepository;
        this.commentRepository = commentRepository;
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

        mapper.map(issueModel, issue);
        issue.setUpdated(new Date());

        updateIssueLinks(issue, issueModel);

        updateIssueLabels(issueModel, issue);

        updateIssueComments(issueModel, issue);

        updateIssueFixVersions(issueModel, issue);

        return mapper.map(repository.save(issue), IssueModel.class);
    }

    private void updateIssueFixVersions(IssueModel issueModel, Issue issue) {
        List<IssueFixVersion> newFixVersions = issueModel.getFixVersions()
                .stream()
                .filter(fixVersion -> fixVersion.getKey() == null)
                .map(fixVersion -> mapper.map(fixVersion, IssueFixVersion.class))
                .collect(Collectors.toList());

        issue.getFixVersions().removeIf(fixVersion -> fixVersion.getId() == null);

        issue.getFixVersions().addAll(fixVersionRepository.saveAll(newFixVersions));
    }

    private void updateIssueComments(IssueModel issueModel, Issue issue) {
        List<IssueComment> newComments = issueModel.getComments()
                .stream()
                .filter(comment -> comment.getKey() == null)
                .map(comment -> mapper.map(comment, IssueComment.class))
                .collect(Collectors.toList());

        issue.getComments().removeIf(comment -> comment.getId() == null);

        newComments.forEach(comment -> comment.setIssue(issue));

        List<IssueComment> toDelete = issue.getComments()
                .stream()
                .filter(comment -> issueModel.getComments()
                        .stream()
                        .noneMatch(modelComment -> modelComment.getKey() != null && comment.getId().equals(modelComment.getKey()))
                ).collect(Collectors.toList());

        issue.getComments().removeAll(toDelete);
        toDelete.forEach(comment -> comment.setIssue(null));

        List<IssueComment> toUpdate = Stream.concat(newComments.stream(), toDelete.stream())
                .collect(Collectors.toList());

        commentRepository.saveAll(toUpdate);
    }

    private void updateIssueLabels(IssueModel issueModel, Issue issue) {
        List<IssueLabel> newLabels = issueModel.getLabels()
                .stream()
                .filter(label -> label.getKey() == null)
                .map(label -> mapper.map(label, IssueLabel.class))
                .collect(Collectors.toList());

        issue.getLabels().removeIf(label -> label.getId() == null);

        issue.getLabels().addAll(labelRepository.saveAll(newLabels));
    }

    private IssueLinkTypeEnum issueLinkTypeEnumFromString(String linkType) {
        if (linkType.equals("Depends on")) {
            return IssueLinkTypeEnum.DEPENDS_ON;
        } else if (linkType.equals("Relates to")) {
            return IssueLinkTypeEnum.RELATES_TO;
        }

        return null;
    }

    private void updateIssueLinks(Issue issue, IssueModel issueModel) {
        List<IssueLink> issueLinks = issue.getIssueLinks();
        for (IssueLinkModel link : issueModel.getIssueLinks()) {
            issueLinks.stream()
                    .filter(issueLink -> issueLink.getId() != null && issueLink.getId().equals(link.getId()))
                    .findFirst()
                    .ifPresent(issueLink -> {
                        String linkedIssueKey = link.getLinkedIssue().getKey();
                        Issue linkedIssue = repository.findByKey(linkedIssueKey)
                                .orElseThrow(() -> new IssueNotFoundException(
                                        String.format("Linked issue with key %s was not found", linkedIssueKey))
                                );
                        issueLink.setLinkedIssue(linkedIssue);
                        issueLink.setLinkType(issueLinkTypeEnumFromString(link.getLinkType()));
                    });
        }
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
