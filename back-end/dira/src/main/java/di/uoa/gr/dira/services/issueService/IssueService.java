package di.uoa.gr.dira.services.issueService;

import com.sun.istack.Nullable;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.repositories.IssueRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService extends BaseService<IssueModel, Issue, Long, IssueRepository> implements IIssueService {
    ProjectRepository projectRepository;

    IssueService(IssueRepository repository, ProjectRepository projectRepository, ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
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
        return getIssueModel(projectId, saved);
    }

    @Override
    public IssueModel findIssueWithProjectId(Long projectId, Long issueId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Issue issue = repository.findById(issueId).orElse(null);

        if (project != null && issue != null) {
            Issue projects_issue = project.getIssues().stream().filter(obj -> obj.equals(issue)).findAny().orElse(null);
            if (projects_issue != null) {
                return mapper.map(projects_issue, IssueModel.class);
            }
        }
        return null;
    }

    @Override
    public IssueModel updateIssueWithProjectId(Long projectId, IssueModel issueModel) {
        // TODO: check if already exists
        return super.save(issueModel);
    }

    @Nullable
    private IssueModel getIssueModel(Long projectId, IssueModel updated) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            List<Issue> projectIssues = project.getIssues();
            if (projectIssues != null) {
                repository.findById(updated.getId()).ifPresent(projectIssues::add);
                projectRepository.save(project);
                return updated;
            }
        }
        return null;
    }

    @Override
    public void deleteIssueWithProjectId(Long projectId, IssueModel issueModel) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Optional<Issue> issue = repository.findById(issueModel.getId());

        if (project != null && issue.isPresent()) {
            project.getIssues().remove(issue);
            super.delete(issueModel);
            projectRepository.save(project);
        }
    }

}
