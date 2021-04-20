package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.services.issueService.IssueService;
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

    @GetMapping
    public @Valid
    ProjectIssuesModel getAllIssuesWithProjectId(@PathVariable Long projectId) {
        return service.findAllIssuesByProjectId(projectId);
    }

    @PostMapping
    public void createIssueWithProjectId(@PathVariable Long projectId, @Valid @RequestBody IssueModel issueModel) {
        service.createIssueWithProjectId(projectId, issueModel);
    }

    @GetMapping("{id}")
    public @Valid IssueModel retrieveIssueWithProjectId(@PathVariable Long id) {
        return service.findIssueWithProjectId(id);
    }
}
