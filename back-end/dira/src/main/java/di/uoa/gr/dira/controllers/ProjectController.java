package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.ProjectModel;
import di.uoa.gr.dira.services.projectService.IProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    final IProjectService service;

    public ProjectController(IProjectService service) {
        this.service = service;
    }

    @GetMapping("all")
    @ResponseBody
    public List<ProjectModel> getAllProjects() {
        return service.findAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public ProjectModel getProjectById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseBody
    public ProjectModel createProject(@RequestBody ProjectModel project) {
        return service.insert(project);
    }
}
