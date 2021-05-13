package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.security.JwtHelper;
import di.uoa.gr.dira.services.projectService.IProjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("projects")
public class ProjectController {
    private final IProjectService service;
    private final JwtHelper jwtHelper;

    public ProjectController(IProjectService service, JwtHelper jwtHelper) {
        this.service = service;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping
    /* TODO: retrieve all projects depending on user's visibility */
    public List<@Valid ProjectModel> getAllProjects() {
        return service.findAll();
    }

    @PostMapping
    public @Valid ProjectModel createProject(
            @Valid @RequestBody ProjectModel project,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.createProject(customerId, project);
    }

    @GetMapping("{projectId}")
    public @Valid
    ProjectModel getProjectById(@PathVariable Long projectId) {
        return service.findById(projectId);
    }

    // TODO: add path variable projectId
    @PutMapping("{projectId}")
    public @Valid ProjectModel updateProjectWithId(@Valid @RequestBody ProjectModel projectModel) {
        return service.updateProjectWithId(projectModel);
    }

    @DeleteMapping("{projectId}")
    // TODO: fix delete
    public void deleteProjectById(@PathVariable Long projectId) {
        service.deleteById(projectId);
    }
}
