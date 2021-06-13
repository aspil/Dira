package di.uoa.gr.dira.configuration.modelMapper.entities.project.permission;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.project.Permission;
import di.uoa.gr.dira.models.project.permission.ProjectUserPermissionModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class ProjectUserPermissionModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<Permission, ProjectUserPermissionModel> typeMap = mapper.createTypeMap(Permission.class, ProjectUserPermissionModel.class);
        typeMap.addMappings(m -> m.map(s -> s.getUser().getId(), ProjectUserPermissionModel::setCustomerId));
    }
}
