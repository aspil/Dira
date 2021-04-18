package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.services.issueService.IssueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/{projectId}/issues")
public class IssueController {
    private final IssueService service;

    public IssueController(IssueService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<IssueModel> getAllIssuesWithProjectId(@PathVariable("projectId") Long projectId) {
        return service.findAllIssuesByProjectId(projectId);
    }
}
