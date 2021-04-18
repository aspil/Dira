package di.uoa.gr.dira.services.issueService;

import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssueModel;
import di.uoa.gr.dira.repositories.IssueRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.util.MapperHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService extends BaseService<IssueModel, Issue, Long, IssueRepository> implements IIssueService {

    ProjectRepository projectRepository;

    IssueService(IssueRepository repository, ProjectRepository projectRepository) {
        super(repository);
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectIssueModel findAllIssuesByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            List<IssueModel> issues = MapperHelper.mapList(mapper, project.getIssues(), IssueModel.class);
            ProjectIssueModel projectIssues = mapper.map(project, ProjectIssueModel.class);
            projectIssues.setIssues(issues);
            return projectIssues;
        }
        return null;
    }

    @Override
    public void createIssueToProject(Long projectId, IssueModel issueModel) {
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
