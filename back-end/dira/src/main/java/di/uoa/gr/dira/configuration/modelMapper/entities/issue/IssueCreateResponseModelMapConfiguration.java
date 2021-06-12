package di.uoa.gr.dira.configuration.modelMapper.entities.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.models.issue.IssueCreateResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class IssueCreateResponseModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<Issue, IssueCreateResponseModel> typeMap = mapper.createTypeMap(Issue.class, IssueCreateResponseModel.class);
        typeMap.addMappings(m -> m.map(s -> s.getAssignee().getName(), IssueCreateResponseModel::setAssignee));
        typeMap.addMappings(m -> m.map(s -> s.getReporter().getName(), IssueCreateResponseModel::setReporter));
    }
}
