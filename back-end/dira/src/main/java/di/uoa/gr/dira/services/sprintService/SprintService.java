package di.uoa.gr.dira.services.sprintService;


import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.entities.sprint.Sprint;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.issue.IssueNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.exceptions.sprint.SprintDoesNotBelongToProjectException;
import di.uoa.gr.dira.exceptions.sprint.SprintNotFoundException;
import di.uoa.gr.dira.models.project.ProjectSprintsModel;
import di.uoa.gr.dira.models.sprint.SprintModel;
import di.uoa.gr.dira.repositories.*;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.services.issueService.IIssueService;
import di.uoa.gr.dira.services.permissionService.IPermissionService;
import di.uoa.gr.dira.shared.PermissionType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class SprintService extends BaseService<SprintModel, Sprint, Long, SprintRepository> implements ISprintService  {
    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final IPermissionService permissionService;
    private final IIssueService issueService;

    public SprintService(SprintRepository repository,
                        ProjectRepository projectRepository,
                        CustomerRepository customerRepository,
                        IPermissionService permissionService,
                        IIssueService issueService,
                        ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
        this.permissionService = permissionService;
        this.issueService = issueService;
    }

    private Project checkPermissions(Long projectId, Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        project.getCustomers()
                .stream()
                .filter(customer1 -> customer1.getId().equals(customerId))
                .findFirst()
                .orElseThrow(ActionNotPermittedException::new);

        return project;
    }

    public ProjectSprintsModel findAllSprintsByProjectId(Long projectId, Long customerId) {
        Project project = checkPermissions(projectId, customerId);

        return mapper.map(project, ProjectSprintsModel.class);
    }

    public SprintModel createSprintWithProjectId(Long projectId, Long customerId, SprintModel sprintModel) {
        Project project = checkPermissions(projectId, customerId);

        if (!permissionService.checkProjectUserPermissions(customerId, project, PermissionType.ADMIN)) {
            throw new ActionNotPermittedException("You need ADMIN permissions in order to create a Sprint");
        }

        Sprint sprint = mapper.map(sprintModel, Sprint.class);
        sprint.setProject(project);
        sprint.setIssues(new ArrayList<>());
        Sprint saved = repository.save(sprint);

        List<Issue> issuesToUpdate = sprintModel.getIssueModels()
                .stream()
                .map(issueModel -> issueService.getRepository()
                        .findById(issueModel.getId())
                        .orElseThrow(() -> new IssueNotFoundException("id", issueModel.getId().toString()))
                ).collect(Collectors.toList());

        issuesToUpdate.forEach(issue -> issue.setBelongsToSprint(saved));
        issueService.getRepository().saveAll(issuesToUpdate);

        return mapper.map(sprint, SprintModel.class);
    }

    public SprintModel findSprintWithProjectId(Long projectId, Long customerId, Long sprintId) {
        Project project = checkPermissions(projectId, customerId);

        Sprint sprint = project.
                getSprints().
                stream().
                filter((sprint1 -> sprint1.getId().equals(sprintId))).
                findAny().
                orElseThrow(() -> new SprintDoesNotBelongToProjectException("sprintId", sprintId.toString()));

        return mapper.map(sprint, SprintModel.class);
    }

    public SprintModel updateSprintWithProjectId(Long projectId, Long customerId, Long sprintId, SprintModel sprintModel) {
        Project project = checkPermissions(projectId, customerId);
        Sprint sprint = repository.findById(sprintId).orElseThrow(() -> new SprintNotFoundException("sprintId", sprintId.toString()));

        if (!permissionService.checkProjectUserPermissions(customerId, project, PermissionType.ADMIN)) {
            throw new ActionNotPermittedException("You need ADMIN permissions in order to update the Sprint");
        }

        mapper.map(sprintModel, sprint);
        updateSprintIssues(sprintModel, sprint);

        return mapper.map(repository.save(sprint), SprintModel.class);
    }

    private void updateSprintIssues(SprintModel sprintModel, Sprint sprint) {
        List<Issue> newIssues = sprintModel.getIssueModels()
                .stream()
                .map(issue -> mapper.map(issue, Issue.class))
                .filter(issue -> issue.getBelongsToSprint() == null)
                .collect(Collectors.toList());

        newIssues.forEach(issue -> issue.setBelongsToSprint(sprint));

        List<Issue> toDelete = sprint.getIssues()
                .stream()
                .filter(issue -> sprintModel.getIssueModels()
                        .stream()
                        .noneMatch(modelIssue -> modelIssue.getKey() != null && issue.getId().equals(modelIssue.getId()))
                ).collect(Collectors.toList());

        sprint.getIssues().removeAll(toDelete);
        toDelete.forEach(issue -> issue.setBelongsToSprint(null));

        List<Issue> toUpdate = Stream.concat(newIssues.stream(), toDelete.stream())
                .collect(Collectors.toList());

        if (!toUpdate.isEmpty()) {
            issueService.getRepository().saveAll(toUpdate);
        }
    }

    public void deleteSprintWithProjectId(Long projectId, Long customerId, Long sprintId) {
        Project project = checkPermissions(projectId, customerId);
        repository.findById(sprintId).orElseThrow(() -> new SprintNotFoundException("sprintId", sprintId.toString()));

        if (!permissionService.checkProjectUserPermissions(customerId, project, PermissionType.ADMIN)) {
            throw new ActionNotPermittedException("You need ADMIN permissions in order to delete the Sprint");
        }

        project.getSprints().removeIf(sprint1 -> sprint1.getId().equals(sprintId));
        projectRepository.save(project);
        repository.deleteById(sprintId);
    }
}
