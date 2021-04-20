package di.uoa.gr.dira.services.issueService;

import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.repositories.IssueRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public void createIssueWithProjectId(Long projectId, IssueModel issueModel) {
        IssueModel saved = super.save(issueModel);
        Project project = projectRepository.findById(projectId).orElse(null);
        //TODO: fix this, maybe we'll need model mapper from model to entity
        if (project != null) {
        }
    }

    @Override
    public IssueModel findIssueWithProjectId(Long issueId) {
        Issue issue = repository.findById(issueId).orElse(null);

        if (issue != null) {
            return mapper.map(issue, IssueModel.class);
        }
        return null;
    }

}
