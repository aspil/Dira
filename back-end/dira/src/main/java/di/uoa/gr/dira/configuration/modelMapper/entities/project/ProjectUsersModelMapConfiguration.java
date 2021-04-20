package di.uoa.gr.dira.configuration.modelMapper.entities.project;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.util.mapper.ListConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class ProjectUsersModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<Project, ProjectUsersModel> typeMap = mapper.createTypeMap(Project.class, ProjectUsersModel.class);
        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, CustomerModel.class))
                .map(Project::getCustomers, ProjectUsersModel::setUsers)
        );
    }
}
