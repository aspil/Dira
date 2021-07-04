package di.uoa.gr.dira.configuration.modelMapper;

import di.uoa.gr.dira.entities.project.Permission;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.models.project.ProjectSprintsModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.models.project.permission.ProjectUserPermissionModel;
import di.uoa.gr.dira.models.sprint.SprintModel;
import di.uoa.gr.dira.util.mapper.ListConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapperConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        configureProjectModelToProjectEntity(mapper);
        configureProjectEntityToProjectUsersModel(mapper);
        configureProjectEntityToProjectIssuesModel(mapper);
        configurePermissionEntityToProjectUserPermissionModel(mapper);
        configureProjectUserPermissionModelToPermissionEntity(mapper);
        configureProjectEntityToProjectSprintsModel(mapper);
    }

    /**
     * Creates the model mapper configuration when mapping a ProjectModel to a Project entity
     *
     * @param mapper The mapper to configure
     */
    private void configureProjectModelToProjectEntity(ModelMapper mapper) {
        TypeMap<ProjectModel, Project> typeMap = mapper.createTypeMap(ProjectModel.class, Project.class);
        typeMap.addMappings(m -> m.skip(ProjectModel::getId, Project::setId));
    }

    /**
     * Creates the model mapper configuration when mapping a Project entity to a ProjectUsersModel model
     *
     * @param mapper The mapper to configure
     */
    private void configureProjectEntityToProjectUsersModel(ModelMapper mapper) {
        TypeMap<Project, ProjectUsersModel> typeMap = mapper.createTypeMap(Project.class, ProjectUsersModel.class);
        typeMap.addMappings(mapping -> mapping.using(ListConverter.withMapper(mapper, CustomerModel.class))
                .map(Project::getCustomers, ProjectUsersModel::setUsers)
        );
    }

    /**
     * Creates the model mapper configuration when mapping a Project entity to a ProjectIssuesModel model
     *
     * @param mapper The mapper to configure
     */
    private void configureProjectEntityToProjectIssuesModel(ModelMapper mapper) {
        TypeMap<Project, ProjectIssuesModel> typeMap = mapper.createTypeMap(Project.class, ProjectIssuesModel.class);
        typeMap.addMappings(mapping -> mapping.using(ListConverter.withMapper(mapper, IssueModel.class))
                .map(Project::getIssues, ProjectIssuesModel::setIssues)
        );
    }

    /**
     * Creates the model mapper configuration when mapping a Project entity to a ProjectSprintsModel model
     *
     * @param mapper The mapper to configure
     */
    private void configureProjectEntityToProjectSprintsModel(ModelMapper mapper) {
        TypeMap<Project, ProjectSprintsModel> typeMap = mapper.createTypeMap(Project.class, ProjectSprintsModel.class);
        typeMap.addMappings(mapping -> mapping.using(ListConverter.withMapper(mapper, SprintModel.class))
                .map(Project::getSprints, ProjectSprintsModel::setSprints)
        );
    }

    /**
     * Creates a model mapper configuration when mapping a Permission entity to ProjectUserPermissionModel model
     *
     * @param mapper The mapper to configure
     */
    private void configurePermissionEntityToProjectUserPermissionModel(ModelMapper mapper) {
        TypeMap<Permission, ProjectUserPermissionModel> typeMap = mapper.createTypeMap(Permission.class, ProjectUserPermissionModel.class);
        typeMap.addMappings(m -> m.map(Permission::getPermission, ProjectUserPermissionModel::setPermissionsFromInteger));
        typeMap.addMappings(m -> m.map(s -> s.getUser().getId(), ProjectUserPermissionModel::setCustomerId));
    }

    private void configureProjectUserPermissionModelToPermissionEntity(ModelMapper mapper) {
        TypeMap<ProjectUserPermissionModel, Permission> typeMap = mapper.createTypeMap(ProjectUserPermissionModel.class, Permission.class);
        typeMap.addMappings(m -> m.map(ProjectUserPermissionModel::getPermissions, Permission::setPermissionFromPermissionSet));
    }
}
