package di.uoa.gr.dira.controllers;


import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.security.JwtHelper;
import di.uoa.gr.dira.services.projectService.IProjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("projects/{projectId}/users")
public class ProjectUserController {
    private final IProjectService service;
    private final JwtHelper jwtHelper;

    public ProjectUserController(IProjectService service, JwtHelper jwtHelper) {
        this.service = service;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping
    public @Valid
    ProjectUsersModel getAllProjectUsers(@PathVariable Long projectId) {
        return service.findUsersByProjectId(projectId);
    }

    @PostMapping
    public void addUserToProjectWithId(
            @PathVariable Long projectId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        Long customerId = jwtHelper.getId(jwtToken);
        service.addUserToProjectWithId(projectId, customerId);
    }

    @DeleteMapping("{userId}")
    public void deleteUserFromProjectWithId(@PathVariable Long projectId, @PathVariable Long userId) {
        service.deleteUserFromProjectWithId(projectId, userId);
    }
}
