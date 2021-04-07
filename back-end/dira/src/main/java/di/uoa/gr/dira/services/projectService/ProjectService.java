//package di.uoa.gr.dira.services.projectService;
//
//import di.uoa.gr.dira.entities.Project;
//import di.uoa.gr.dira.models.ProjectModel;
//import di.uoa.gr.dira.repositories.ProjectRepository;
//import di.uoa.gr.dira.services.projectService.IProjectService;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ProjectService implements IProjectService {
//    ProjectRepository repository;
//
//    ProjectService(ProjectRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public List<ProjectModel> findAll() {
//        List<Project> projects = repository.findAll();
//        List<ProjectModel> models = new ArrayList<>();
//        for (Project project : projects) {
//            ProjectModel model = new ProjectModel();
//            model.setName(project.getName());
//            model.setDescription(project.getDescription());
//            model.setUsers(project.getUsers());
//            models.add(model);
//        }
//        return models;
//    }
//
//    @Override
//    public ProjectModel findById(String s) {
//        return null;
//    }
//
//    @Override
//    public ProjectModel insert(ProjectModel projectModel) {
//        return null;
//    }
//
//    @Override
//    public ProjectModel update(ProjectModel projectModel) {
//        return null;
//    }
//
//    @Override
//    public void deleteById(String s) {
//
//    }
//
//    @Override
//    public void delete(ProjectModel projectModel) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends ProjectModel> projectModels) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//}