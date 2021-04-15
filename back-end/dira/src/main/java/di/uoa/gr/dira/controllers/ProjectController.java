package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.ProjectModel;
import di.uoa.gr.dira.services.projectService.IProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    private final IProjectService service;

    public ProjectController(IProjectService service) {
        this.service = service;
    }

    @GetMapping("all")
    @ResponseBody
    public List<ProjectModel> getAllProjects() {
        return service.findAll();
    }

    @PostMapping("all")
    @ResponseBody
    public ProjectModel createProject(@RequestBody ProjectModel project) {
        return service.save(project);
    }

    @DeleteMapping("all")
    public void deleteAllProjects() {
        service.deleteAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public ProjectModel getProjectById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteProjectById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
