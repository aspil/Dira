package di.uoa.gr.dira.controllers;


import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.security.JwtHelper;
import di.uoa.gr.dira.services.projectService.IProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @ApiOperation(
            value = "Retrieves all the users inside the project with the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping
    public @Valid ProjectUsersModel getAllProjectUsers(@PathVariable Long projectId) {
        return service.findUsersByProjectId(projectId);
    }

    @ApiOperation(
            value = "Adds a user to the project with the given id"
    )
    @PostMapping("{userId}")
    public void addUserToProjectWithId(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        Long inviterId = jwtHelper.getId(jwtToken);
        service.addUserToProjectWithId(projectId, inviterId, userId);
    }

    @ApiOperation(
            value = "Adds the user with the given email to the project"
    )
    @PostMapping
    public void addUserToProjectWithEmail(
            @PathVariable Long projectId,
            @RequestParam("email") String email,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        Long inviterId = jwtHelper.getId(jwtToken);
        service.addUserToProjectWithEmail(projectId, inviterId, email);
    }


    @ApiOperation(
            value = "Deletes a user from the project with the given id"
    )
    @DeleteMapping("{userId}")
    public void deleteUserFromProjectWithId(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        Long projectOwnerId = jwtHelper.getId(jwtToken);
        service.deleteUserFromProjectWithId(projectId, projectOwnerId, userId);
    }
}
