package di.uoa.gr.dira.configuration.modelMapper.entities.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.IssueLabel;
import di.uoa.gr.dira.util.LongStringPair;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class IssueLabelLongStringPairModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<IssueLabel, LongStringPair> typeMap = mapper.createTypeMap(IssueLabel.class, LongStringPair.class);
        typeMap.addMappings(m -> m.map(IssueLabel::getId, LongStringPair::setKey));
        typeMap.addMappings(m -> m.map(IssueLabel::getName, LongStringPair::setValue));
    }
}
