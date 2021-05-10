package di.uoa.gr.dira.controllers;


import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.services.projectService.IProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("projects/{projectId}/users")
public class ProjectUserController {
    private final IProjectService service;

    public ProjectUserController(IProjectService service) {
        this.service = service;
    }

    @GetMapping
    public @Valid
    ProjectUsersModel getAllProjectUsers(@PathVariable Long projectId) {
        return service.findUsersByProjectId(projectId);
    }

    @PostMapping
    public void addUserToProjectWithId(@PathVariable Long projectId, Long userId) {
        service.addUserToProjectWithId(projectId, userId);
    }

    @DeleteMapping("{userId}")
    public void deleteUserFromProjectWithId(@PathVariable Long projectId, @PathVariable Long userId) {
        service.deleteUserFromProjectWithId(projectId, userId);
    }
}
