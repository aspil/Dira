package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.services.projectService.IProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("projects")
public class ProjectController {
    private final IProjectService service;

    public ProjectController(IProjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProjectModel> getAllProjects() {
        return service.findAll();
    }

    @PostMapping
    public ProjectModel createProject(@RequestBody ProjectModel project) {
        return service.save(project);
    }

    @DeleteMapping
    public void deleteAllProjects() {
        service.deleteAll();
    }

    @GetMapping("{id}")
    public ProjectModel getProjectById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteProjectById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("{id}/users")
    public ProjectUsersModel getAllProjectUsers(@PathVariable Long id) {
        return service.findUsersByProjectId(id);
    }

    @PostMapping("{id}/users")
    public void addUserToProjectWithId(@PathVariable Long id) {
        service.addUserToProjectWithId(id);
    }
}
