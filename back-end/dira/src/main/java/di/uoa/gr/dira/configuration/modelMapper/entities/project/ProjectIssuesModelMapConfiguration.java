package di.uoa.gr.dira.configuration.modelMapper.entities.project;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.project.ProjectIssuesModel;
import di.uoa.gr.dira.util.mapper.ListConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class ProjectIssuesModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<Project, ProjectIssuesModel> typeMap = mapper.createTypeMap(Project.class, ProjectIssuesModel.class);
        typeMap.addMappings(mapping -> mapping.using(ListConverter.withMapper(mapper, IssueModel.class))
                .map(Project::getIssues, ProjectIssuesModel::setIssues)
        );
    }
}
