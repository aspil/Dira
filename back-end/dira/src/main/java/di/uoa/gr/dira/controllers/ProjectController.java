package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.security.JwtHelper;
import di.uoa.gr.dira.services.projectService.IProjectService;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("projects")
public class ProjectController {
    private final IProjectService service;
    private final JwtHelper jwtHelper;

    public ProjectController(IProjectService service, JwtHelper jwtHelper) {
        this.service = service;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping
    public List<@Valid ProjectModel> getAllProjects(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        SubscriptionPlanEnum customerSubscriptionPlan = jwtHelper.getSubscriptionPlan(jwtToken);
        if (customerSubscriptionPlan == SubscriptionPlanEnum.STANDARD) {
            return service.findAllPublicProjects();
        } else {
            return service.findAll();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @Valid ProjectModel createProject(
            @Valid @RequestBody ProjectModel project,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.createProject(customerId, project);
    }

    @GetMapping("{projectId}")
    public @Valid ProjectModel getProjectById(
            @PathVariable Long projectId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        SubscriptionPlanEnum customerSubscriptionPlan = jwtHelper.getSubscriptionPlan(jwtToken);
        return service.getProject(projectId, customerSubscriptionPlan);
    }

    @PutMapping("{projectId}")
    public @Valid ProjectModel updateProjectWithId(
            @PathVariable Long projectId,
            @RequestBody ProjectModel projectModel
    ) {
        return service.updateProjectWithId(projectId, projectModel);
    }

    @DeleteMapping("{projectId}")
    public void deleteProjectById(
            @PathVariable Long projectId
    ) {
        service.deleteProjectWithId(projectId);
    }
}
