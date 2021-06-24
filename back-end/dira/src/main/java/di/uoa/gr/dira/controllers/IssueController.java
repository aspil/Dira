package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.exceptions.issue.IssueNotFoundException;
import di.uoa.gr.dira.models.issue.IssueCreateModel;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.security.JwtHelper;
import di.uoa.gr.dira.services.issueService.IIssueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("projects/{projectId}/issues")
public class IssueController {
    private final IIssueService service;
    private final JwtHelper jwtHelper;

    public IssueController(IIssueService service, JwtHelper jwtHelper) {
        this.service = service;
        this.jwtHelper = jwtHelper;
    }

    @ApiOperation(
            value = "Retrieves all the issues under a project",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping
    public @Valid ProjectIssuesModel getAllIssuesWithProjectId(@PathVariable Long projectId) {
        return service.findAllIssuesByProjectId(projectId);
    }

    @ApiOperation(
            value = "Creates a new issue under the project with the given id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PostMapping
    public @Valid IssueModel createIssueWithProjectId(
            @PathVariable Long projectId,
            @Valid @RequestBody IssueCreateModel issueCreateModel,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.createIssueWithProjectId(projectId, customerId, issueCreateModel);
    }

    @ApiOperation(
            value = "Retrieves the issue with the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping("{issueId}")
    public @Valid IssueModel retrieveIssueWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long issueId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.findIssueWithProjectId(projectId, customerId, issueId)
                .orElseThrow(() -> new IssueNotFoundException("issueId", issueId.toString()));
    }

    @ApiOperation(
            value = "Updates the issue with the given id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PutMapping("{issueId}")
    public @Valid IssueModel updateIssueWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long issueId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
            @Valid @RequestBody IssueModel issueModel) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.updateIssueWithProjectId(projectId, customerId, issueId, issueModel);
    }

    @ApiOperation(
            value = "Deletes the issue with the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @DeleteMapping("{issueId}")
    public void deleteIssueWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long issueId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        Long customerId = jwtHelper.getId(jwtToken);
        service.deleteIssueWithProjectId(projectId, customerId, issueId);
    }
}
