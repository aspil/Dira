package di.uoa.gr.dira.services.permissionService;

import di.uoa.gr.dira.entities.project.Permission;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.project.ProjectUserPermissionModel;
import di.uoa.gr.dira.repositories.PermissionRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionService extends BaseService<ProjectUserPermissionModel, Permission, Long, PermissionRepository> implements IPermissionService {
    ProjectRepository projectRepository;

    PermissionService(PermissionRepository repository, ProjectRepository projectRepository, ModelMapper mapper) {
        super(repository, mapper);
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectUserPermissionModel> getProjectPermissionsForUsers(Long projectId) {
        return projectRepository.findById(projectId)
                .map(project -> MapperHelper.<Permission, ProjectUserPermissionModel>mapList(mapper, project.getPermissions(), ProjectUserPermissionModel.class))
                .orElse(null);
    }

    @Override
    public void createUserPermission(Long projectId, ProjectUserPermissionModel userPermissionModel) {
        ProjectUserPermissionModel saved = super.save(userPermissionModel);
        getPermissionModel(projectId, saved);
    }

    private void getPermissionModel(Long projectId, ProjectUserPermissionModel saved) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            List<Permission> projectPermission = project.getPermissions();
            if (projectPermission != null) {
                repository.findById(saved.getId()).ifPresent(projectPermission::add);
            }
        }
    }

    @Override
    public @Valid ProjectUserPermissionModel updateUserPermission(Long projectId, ProjectUserPermissionModel userPermissionModel) {
        super.delete(userPermissionModel);
        ProjectUserPermissionModel updated = super.save(userPermissionModel);
        Optional<Permission> permission = repository.findById(updated.getId());
        if (!permission.isPresent()) return null;

        getPermissionModel(projectId, updated);
        return updated;
    }

    @Override
    public void deleteUserPermission(Long projectId, Long permissionId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Permission permission = repository.findById(permissionId).orElse(null);

        ProjectUserPermissionModel userPermissionModel = mapper.map(permission, ProjectUserPermissionModel.class);
        if (project != null && permission != null) {
            project.getPermissions().remove(permission);
            super.delete(userPermissionModel);
        }
    }
}
