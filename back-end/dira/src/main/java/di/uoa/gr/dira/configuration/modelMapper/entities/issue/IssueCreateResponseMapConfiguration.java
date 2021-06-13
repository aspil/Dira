package di.uoa.gr.dira.configuration.modelMapper.entities.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.models.issue.IssueModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class IssueCreateResponseMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<Issue, IssueModel> typeMap = mapper.createTypeMap(Issue.class, IssueModel.class);
        typeMap.addMappings(m -> m.map(s -> s.getAssignee().getName(), IssueModel::setAssignee));
        typeMap.addMappings(m -> m.map(s -> s.getReporter().getName(), IssueModel::setReporter));
    }
}
