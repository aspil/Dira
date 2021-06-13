package di.uoa.gr.dira.configuration.modelMapper.entities.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.IssueComment;
import di.uoa.gr.dira.util.LongStringPair;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class IssueCommentLongStringPairMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<IssueComment, LongStringPair> typeMap = mapper.createTypeMap(IssueComment.class, LongStringPair.class);
        typeMap.addMappings(m -> m.map(IssueComment::getId, LongStringPair::setKey));
        typeMap.addMappings(m -> m.map(IssueComment::getComment, LongStringPair::setValue));
    }
}
