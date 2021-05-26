package di.uoa.gr.dira.services.issueService;

import com.sun.istack.Nullable;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.issue.IssueNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.repositories.IssueRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IssueService extends BaseService<IssueModel, Issue, Long, IssueRepository> implements IIssueService {
    ProjectRepository projectRepository;

    IssueService(IssueRepository repository, ProjectRepository projectRepository, ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
    }

    @Nullable
    private IssueModel addIssueToProject(Long projectId, IssueModel updated) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        List<Issue> projectIssues = project.getIssues();
        if (projectIssues != null) {
            repository.findById(updated.getId()).ifPresent(projectIssues::add);
            projectRepository.save(project);
            return updated;
        }

        return null;
    }

    @Override
    public ProjectIssuesModel findAllIssuesByProjectId(Long projectId) {
        return projectRepository.findById(projectId)
                .map(project -> mapper.map(project, ProjectIssuesModel.class))
                .orElse(null);
    }

    @Override
    public IssueModel createIssueWithProjectId(Long projectId, IssueModel issueModel) {
        IssueModel saved = super.save(issueModel);
        return addIssueToProject(projectId, saved);
    }

    @Override
    public IssueModel findIssueWithProjectId(Long projectId, Long issueId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        Issue issue = project.getIssues()
                .stream()
                .filter(obj -> obj.getId().equals(issueId))
                .findAny()
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));

        return mapper.map(issue, IssueModel.class);
    }

    @Override
    public IssueModel updateIssueWithProjectId(Long projectId, Long issueId, IssueModel issueModel) {
        super.delete(issueModel);
        IssueModel updatedIssue = super.save(issueModel);
        return addIssueToProject(projectId, updatedIssue);
    }

    @Override
    public void deleteIssueWithProjectId(Long projectId, Long issueId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        List<Issue> projectIssues = project.getIssues();
        Issue issue = projectIssues.stream().filter(obj -> obj.getId().equals(issueId)).findAny().orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));
        projectIssues.remove(issue);

        super.delete(mapper.map(issue, IssueModel.class));
        projectRepository.save(project);
    }}
