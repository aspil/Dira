package di.uoa.gr.dira.services.projectService;

import di.uoa.gr.dira.entities.Project;
import di.uoa.gr.dira.models.ProjectModel;
import di.uoa.gr.dira.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements IProjectService {
    ProjectRepository repository;

    ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProjectModel> findAll() {
        List<Project> projects = repository.findAll();
        List<ProjectModel> models = new ArrayList<>();
        for (Project project : projects) {
            ProjectModel model = new ProjectModel();
            model.setKey(project.getKey());
            model.setName(project.getName());
            model.setDescription(project.getDescription());
            model.setCustomers(project.getCustomers());
            models.add(model);
        }
        return models;
    }

    @Override
    public ProjectModel findById(Long id) {
        Project project = repository.findById(id).orElse(null);
        return project != null ? new ProjectModel(project) : null;
    }

    @Override
    public ProjectModel save(ProjectModel projectModel) {
        Project project = repository.save(new Project(projectModel));
        return new ProjectModel(project);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(ProjectModel projectModel) {

    }

    @Override
    public void deleteAll(Iterable<? extends ProjectModel> projectModels) {

    }

    @Override
    public void deleteAll() {

    }
}