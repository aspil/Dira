package di.uoa.gr.dira.services.permissionService;

import di.uoa.gr.dira.entities.project.Permission;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.exceptions.project.permission.PermissionNotFoundException;
import di.uoa.gr.dira.models.project.ProjectUserPermissionModel;
import di.uoa.gr.dira.repositories.PermissionRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class PermissionService extends BaseService<ProjectUserPermissionModel, Permission, Long, PermissionRepository> implements IPermissionService {
    ProjectRepository projectRepository;

    PermissionService(PermissionRepository repository, ProjectRepository projectRepository, ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectUserPermissionModel> getProjectPermissionsForUsers(Long projectId) {
        return projectRepository.findById(projectId).map(project -> MapperHelper.<Permission, ProjectUserPermissionModel>mapList(mapper, project.getPermissions(), ProjectUserPermissionModel.class))
                .orElseThrow(() -> new PermissionNotFoundException("projectId", projectId.toString()));
    }

    private void addPermissionModelToProject(Long projectId, ProjectUserPermissionModel saved) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("id", projectId.toString()));

        List<Permission> projectPermission = project.getPermissions();
        if (projectPermission != null) {
            repository.findById(saved.getId()).ifPresent(projectPermission::add);
        }

    }

    @Override
    public void createUserPermission(Long projectId, ProjectUserPermissionModel userPermissionModel) {
        ProjectUserPermissionModel saved = super.save(userPermissionModel);
        addPermissionModelToProject(projectId, saved);
    }

    @Override
    public @Valid ProjectUserPermissionModel updateUserPermission(Long projectId, Long permissionId, ProjectUserPermissionModel userPermissionModel) {
        super.delete(userPermissionModel);
        ProjectUserPermissionModel updated = super.save(userPermissionModel);
        Permission permission = repository.findById(updated.getId()).orElseThrow(() -> new PermissionNotFoundException("permissionId", permissionId.toString()));

        addPermissionModelToProject(projectId, updated);
        return updated;
    }

    @Override
    public void deleteUserPermission(Long projectId, Long permissionId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        Permission permission = project.getPermissions()
                .stream()
                .filter(obj -> obj.getId().equals(permissionId))
                .findAny()
                .orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        project.getPermissions().remove(permission);
        projectRepository.save(project);

        ProjectUserPermissionModel userPermissionModel = mapper.map(permission, ProjectUserPermissionModel.class);
        super.delete(userPermissionModel);
    }
}
