package di.uoa.gr.dira.controllers;


import di.uoa.gr.dira.models.project.ProjectUserPermissionModel;
import di.uoa.gr.dira.services.permissionService.IPermissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("projects/{projectId}/users/permission")
public class ProjectUserPermissionController {
    private final IPermissionService service;

    public ProjectUserPermissionController(IPermissionService service) {
        this.service = service;
    }

    @GetMapping
    public List<@Valid ProjectUserPermissionModel> getProjectPermissionsForUsers(@PathVariable Long projectId) {
        return service.getProjectPermissionsForUsers(projectId);
    }

    @PostMapping
    public void createUserPermission(@PathVariable Long projectId, @Valid @RequestBody ProjectUserPermissionModel userPermissionModel) {
        service.createUserPermission(projectId, userPermissionModel);
    }

    @PutMapping("{permissionId}")
    public @Valid ProjectUserPermissionModel updateUserPermission(@PathVariable Long projectId,
                                                                  @PathVariable Long permissionId,
                                                                  @Valid @RequestBody ProjectUserPermissionModel userPermissionModel) {
        return service.updateUserPermission(projectId, permissionId, userPermissionModel);
    }

    @DeleteMapping("{permissionId}")
    public void deleteUserPermission(@PathVariable Long projectId, @PathVariable Long permissionId) {
        service.deleteUserPermission(projectId, permissionId);
    }
}
