package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.issue.IssueRequestModel;
import di.uoa.gr.dira.models.issue.IssueResponseModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.security.JwtHelper;
import di.uoa.gr.dira.services.issueService.IssueService;
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
    private final IssueService service;
    private final JwtHelper jwtHelper;

    public IssueController(IssueService service, JwtHelper jwtHelper) {
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
    public @Valid IssueResponseModel createIssueWithProjectId(
            @PathVariable Long projectId,
            @Valid @RequestBody IssueRequestModel issueRequestModel,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.createIssueWithProjectId(projectId, customerId, issueRequestModel);
    }

    @ApiOperation(
            value = "Retrieves the issue with the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping("{issueId}")
    public @Valid IssueResponseModel retrieveIssueWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long issueId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.findIssueWithProjectId(projectId, customerId, issueId);
    }

    @ApiOperation(
            value = "Updates the issue with the given id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PutMapping("{issueId}")
    public @Valid IssueResponseModel updateIssueWithProjectId(
            @PathVariable Long projectId,
            @PathVariable Long issueId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
            @Valid @RequestBody IssueRequestModel issueRequestModel) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.updateIssueWithProjectId(projectId, customerId, issueId, issueRequestModel);
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
