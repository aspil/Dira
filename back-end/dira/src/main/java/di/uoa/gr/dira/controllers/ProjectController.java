package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.security.JwtHelper;
import di.uoa.gr.dira.services.projectService.IProjectService;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @ApiOperation(
            value = "Retrieves all the projects in the system. Projects are filtered based on their visibility and the user's subscription plan",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping
    public List<@Valid ProjectModel> getAllProjects(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        SubscriptionPlanEnum customerSubscriptionPlan = jwtHelper.getSubscriptionPlan(jwtToken);
        if (customerSubscriptionPlan == SubscriptionPlanEnum.STANDARD) {
            return service.findAllPublicProjects();
        } else {
            return service.findAll();
        }
    }

    @ApiOperation(
            value = "Creates a new project and assigns the user that created it in it",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @Valid ProjectModel createProject(
            @Valid @RequestBody ProjectModel project,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        Long customerId = jwtHelper.getId(jwtToken);
        return service.createProject(customerId, project);
    }

    @ApiOperation(
            value = "Retrieves the project by the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping("{projectId}")
    public @Valid ProjectModel getProjectById(
            @PathVariable Long projectId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken
    ) {
        SubscriptionPlanEnum customerSubscriptionPlan = jwtHelper.getSubscriptionPlan(jwtToken);
        return service.getProject(projectId, customerSubscriptionPlan);
    }

    @ApiOperation(
            value = "Update the project by the given id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PutMapping("{projectId}")
    public @Valid ProjectModel updateProjectWithId(
            @PathVariable Long projectId,
            @RequestBody ProjectModel projectModel
    ) {
        return service.updateProjectWithId(projectId, projectModel);
    }

    @ApiOperation(
            value = "Delete the project by the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @DeleteMapping("{projectId}")
    public void deleteProjectById(
            @PathVariable Long projectId
    ) {
        service.deleteProjectWithId(projectId);
    }
}
