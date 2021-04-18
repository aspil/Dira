package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.entities.Issue;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssueModel;
import di.uoa.gr.dira.services.issueService.IssueService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("project/{projectId}/issues")
public class IssueController {
    private final IssueService service;

    public IssueController(IssueService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public ProjectIssueModel getAllIssuesWithProjectId(@PathVariable("projectId") Long projectId) {
        return service.findAllIssuesByProjectId(projectId);
    }

    @PostMapping
    @ResponseBody
    public IssueModel createIssueWithProjectId(@PathVariable("projectId") Long projectId, @RequestBody IssueModel issueModel) {
        ProjectIssueModel projectIssueModel = service.createIssueToProject(projectId, issueModel);
        return projectIssueModel.getIssues().get(issueModel);

    }
}
