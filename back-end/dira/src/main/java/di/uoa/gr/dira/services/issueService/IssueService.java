package di.uoa.gr.dira.services.issueService;

import com.sun.istack.Nullable;
import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.issue.Issue;
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
    private IssueResponseModel addIssueToProject(Long projectId, Issue updated) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
        updated.setProject(project);
        List<Issue> projectIssues = project.getIssues();
        if (projectIssues != null) {
//            if (projectIssues.contains(updated))
//            repository.findById(updated.getId()).ifPresent(projectIssues::add);

            int issueIdx = projectIssues.indexOf(updated);
            if (issueIdx != -1) {
                projectIssues.set(issueIdx, updated);   /* If issue exists, set instead of re adding to list */
            } else {
                projectIssues.add(updated);
            }
            projectRepository.save(project);
            return mapper.map(updated, IssueResponseModel.class);
        }

        return null;
    }

    @Override
    public ProjectIssuesModel findAllIssuesByProjectId(Long projectId) {
        return projectRepository.findById(projectId)
                .map(project -> mapper.map(project, ProjectIssuesModel.class))
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));
    }

    public IssueResponseModel createIssueWithProjectId(Long projectId, Long customerId, IssueRequestModel issueRequestModel) {
        Issue newIssue = mapper.map(issueRequestModel, Issue.class);
        /* Start populating the new issue */
        Customer reporter = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));
        newIssue.setReporter(reporter);

        newIssue.setCreated(new Date());
        newIssue.setUpdated(newIssue.getCreated());

//        if (newIssue.getEpic() != null) {
//            Issue epicIssue = repository.findById(newIssue.getEpic().getId()).orElseThrow(() -> new IssueNotFoundException("issueId", newIssue.getEpic().getId().toString()));
//            epicIssue.getIssueLinks().add(mapper.map(epicIssue, IssueLink.class));
//            repository.save(epicIssue);
//        }

        newIssue = repository.save(newIssue);
        return addIssueToProject(projectId, newIssue);
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
        return addIssueToProject(projectId, updatedIssue);
    }

    @Override
    public void deleteIssueWithProjectId(Long projectId, Long issueId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        List<Issue> projectIssues = project.getIssues();
        Issue issue = projectIssues.stream().filter(obj -> obj.getId().equals(issueId)).findAny().orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));
        projectIssues.remove(issue);

        repository.delete(issue);
        projectRepository.save(project);
    }}
