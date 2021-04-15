package di.uoa.gr.dira.services.projectService;

import di.uoa.gr.dira.entities.Project;
import di.uoa.gr.dira.models.ProjectModel;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import org.springframework.stereotype.Service;


@Service
public class ProjectService extends BaseService<ProjectModel, Project, Long, ProjectRepository> implements IProjectService {
    ProjectRepository repository;

    ProjectService(ProjectRepository repository) {
        super(repository);
    }
}