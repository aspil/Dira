package di.uoa.gr.dira.configuration.modelMapper.entities.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.issue.IssueResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class IssueResponseModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<Issue, IssueResponseModel> typeMap = mapper.createTypeMap(Issue.class, IssueResponseModel.class);
        typeMap.addMappings(mapping -> mapping.map(ent -> ent.getProject().getName(), IssueResponseModel::setProjectName));
        typeMap.addMappings(mapping -> mapping.map(ent -> ent.getReporter().getUsername(), IssueResponseModel::setReporter));
    }
}
