package di.uoa.gr.dira.configuration.modelMapper.models.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.models.issue.IssueCreateModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class IssueCreateModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<IssueCreateModel, Issue> typeMap = mapper.createTypeMap(IssueCreateModel.class, Issue.class);
        typeMap.addMappings(m -> m.skip(Issue::setProject));
        typeMap.addMappings(m -> m.skip(Issue::setEpic));
        typeMap.addMappings(m -> m.skip(Issue::setAssignee));
        typeMap.addMappings(m -> m.skip(Issue::setReporter));
    }
}
