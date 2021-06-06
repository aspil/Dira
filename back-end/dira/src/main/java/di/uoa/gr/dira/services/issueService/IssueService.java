package di.uoa.gr.dira.services.issueService;

import com.sun.istack.Nullable;
import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.issue.IssueLink;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.issue.IssueNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.models.issue.IssueRequestModel;
import di.uoa.gr.dira.models.issue.IssueResponseModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.IssueRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class IssueService extends BaseService<IssueRequestModel, Issue, Long, IssueRepository> implements IIssueService {
    ProjectRepository projectRepository;
    CustomerRepository customerRepository;

    IssueService(IssueRepository repository,
                 ProjectRepository projectRepository,
                 CustomerRepository customerRepository,
                 ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
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

//    @Override
//    public ProjectIssuesModel findAllIssuesByUserId(Long userId) {
//        return customerRepository.findById(userId)
//                .map(project -> mapper.map(project, ProjectIssuesModel.class))
//                .orElseThrow(() -> new ProjectNotFoundException("projectId", userId.toString()));
//    }

    public IssueResponseModel createIssueWithProjectId(Long projectId, Long customerId, IssueRequestModel issueRequestModel) {
        Issue newIssue = mapper.map(issueRequestModel, Issue.class);
        /* Start populating the new issue entity */
        Customer reporter = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));
        newIssue.setReporter(reporter);
        newIssue.setCreated(new Date());
        newIssue.setUpdated(newIssue.getCreated());

//        if (newIssue.getEpic() != null) {
//            Long epicId = newIssue.getEpic().getId();
//            Issue epicIssue = repository.findById(newIssue.getEpic().getId()).orElseThrow(() -> new IssueNotFoundException("issueId", epicId.toString()));
//            epicIssue.getIssueLinks().add(mapper.map(epicIssue, IssueLink.class));
//            repository.save(epicIssue);
//        }
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
        newIssue.setProject(project);
        newIssue = repository.save(newIssue);
        addIssueToProject(project, newIssue);
        return mapper.map(newIssue, IssueResponseModel.class);
    }

    @Override
    public IssueResponseModel findIssueWithProjectId(Long projectId, Long issueId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        Issue issue = project.getIssues()
                .stream()
                .filter(obj -> obj.getId().equals(issueId))
                .findAny()
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));

        return mapper.map(issue, IssueResponseModel.class);
    }

    @Override
    public IssueResponseModel updateIssueWithProjectId(Long projectId, Long issueId, IssueRequestModel issueRequestModel) {
        Issue updatedIssue = mapper.map(issueRequestModel, Issue.class);
        updatedIssue.setUpdated(new Date());
        updatedIssue = repository.save(updatedIssue);

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
        addIssueToProject(project, updatedIssue);

        return mapper.map(updatedIssue, IssueResponseModel.class);
    }

    @Override
    public void deleteIssueWithProjectId(Long projectId, Long issueId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        List<Issue> projectIssues = project.getIssues();
        Issue issue = projectIssues.stream()
                .filter(obj -> obj.getId().equals(issueId))
                .findAny()
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));

        projectIssues.remove(issue);

        repository.delete(issue);
        projectRepository.save(project);
    }}
