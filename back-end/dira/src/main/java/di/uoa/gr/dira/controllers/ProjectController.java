package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.services.projectService.IProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("projects")
public class ProjectController {
    private final IProjectService service;

    public ProjectController(IProjectService service) {
        this.service = service;
    }

    @GetMapping
    /* TODO: retrieve all projects depending on user's visibility */
    public List<@Valid ProjectModel> getAllProjects() {
        return service.findAll();
    }

    @PostMapping
    public @Valid
    // TODO: fix this
    ProjectModel createProject(@Valid @RequestBody ProjectModel project) {
        return service.save(project);
    }

    @GetMapping("{projectId}")
    public @Valid
    ProjectModel getProjectById(@PathVariable Long projectId) {
        return service.findById(projectId);
    }

    @PutMapping("{projectId}")
    public @Valid
    // TODO: add path variable projectId
    ProjectModel updateProjectWithId(@Valid @RequestBody ProjectModel projectModel) {
        return service.updateProjectWithId(projectModel);
    }

    @DeleteMapping("{projectId}")
    // TODO: fix delete
    public void deleteProjectById(@PathVariable Long projectId) {
        service.deleteById(projectId);
    }
}
