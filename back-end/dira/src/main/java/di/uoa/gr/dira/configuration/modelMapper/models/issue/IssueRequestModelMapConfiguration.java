package di.uoa.gr.dira.configuration.modelMapper.models.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.models.issue.IssueRequestModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class IssueRequestModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<IssueRequestModel, Issue> typeMap = mapper.createTypeMap(IssueRequestModel.class, Issue.class);
        typeMap.addMappings(mapping -> mapping.skip(Issue::setLabels));
    }
}
