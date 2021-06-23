package di.uoa.gr.dira.controllers;


import di.uoa.gr.dira.models.sprint.SprintModel;
import di.uoa.gr.dira.security.JwtHelper;
import di.uoa.gr.dira.services.sprintService.ISprintService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Validated
@RestController
@RequestMapping("/projects/{projectId}/sprints")
public class SprintController {
    private final ISprintService service;
    private final JwtHelper jwtHelper;

    public SprintController(ISprintService service, JwtHelper jwtHelper) {
        this.service = service;
        this.jwtHelper = jwtHelper;
    }

    @ApiOperation(
            value = "Retrieves all the sprints under a project",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping
    public @Valid List<SprintModel> getAllSprintsWithProjectId(
            @PathVariable Long projectId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.findAllSprintsByProjectId(projectId, customerId);
    }

    @ApiOperation(
            value = "Creates a new Sprint under the project with the given id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PostMapping
    public @Valid SprintModel createSprintWithProjectId(
            @PathVariable Long projectId,
            @Valid @RequestBody SprintModel sprintModel,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.createSprintWithProjectId(projectId, customerId, sprintModel);
    }

    @ApiOperation(
            value = "Retrieves the Sprint with the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping("{sprintId}")
    public @Valid SprintModel retrieveSprintWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long sprintId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.findSprintWithProjectId(projectId, customerId, sprintId);
    }

    @ApiOperation(
            value = "Updates the Sprint with the given id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PutMapping("{sprintId}")
    public @Valid SprintModel updateSprintWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long sprintId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
            @Valid @RequestBody SprintModel sprintModel) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.updateSprintWithProjectId(projectId, customerId, sprintId, sprintModel);
    }

    @ApiOperation(
            value = "Deletes the Sprint with the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @DeleteMapping("{sprintId}")
    public void deleteSprintWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long sprintId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        Long customerId = jwtHelper.getId(jwtToken);
        service.deleteSprintWithProjectId(projectId, customerId, sprintId);
    }
}
