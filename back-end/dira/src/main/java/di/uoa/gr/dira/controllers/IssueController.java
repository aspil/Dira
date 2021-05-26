package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.services.issueService.IssueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("projects/{projectId}/issues")
public class IssueController {
    private final IssueService service;

    public IssueController(IssueService service) {
        this.service = service;
    }

    @ApiOperation(
            value = "Retrieves all the issues under a project",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping
    public @Valid ProjectIssuesModel getAllIssuesWithProjectId(
            @PathVariable Long projectId) {
        return service.findAllIssuesByProjectId(projectId);
    }

    @ApiOperation(
            value = "Creates a new issue under the project with the given id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PostMapping
    public @Valid
    IssueModel createIssueWithProjectId(
            @PathVariable Long projectId,
            @Valid @RequestBody IssueModel issueModel) {
        return service.createIssueWithProjectId(projectId, issueModel);
    }

    @ApiOperation(
            value = "Retrieves the issue with the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping("{issueId}")
    public @Valid
    IssueModel retrieveIssueWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long issueId) {
        return service.findIssueWithProjectId(projectId, issueId);
    }

    @ApiOperation(
            value = "Updates the issue with the given id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PutMapping("{issueId}")
    public @Valid
    IssueModel updateIssueWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long issueId,
            @Valid @RequestBody IssueModel issueModel) {
        return service.updateIssueWithProjectId(projectId, issueId, issueModel);
    }

    @ApiOperation(
            value = "Deletes the issue with the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @DeleteMapping("{issueId}")
    public @Valid
    void deleteIssueWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long issueId) {
        service.deleteIssueWithProjectId(projectId, issueId);
    }
}
