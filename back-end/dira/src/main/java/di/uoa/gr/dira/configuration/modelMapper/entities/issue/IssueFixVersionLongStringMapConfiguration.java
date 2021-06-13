package di.uoa.gr.dira.configuration.modelMapper.entities.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.IssueFixVersion;
import di.uoa.gr.dira.util.LongStringPair;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class IssueFixVersionLongStringMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<IssueFixVersion, LongStringPair> typeMap = mapper.createTypeMap(IssueFixVersion.class, LongStringPair.class);
        typeMap.addMappings(m -> m.map(IssueFixVersion::getId, LongStringPair::setKey));
        typeMap.addMappings(m -> m.map(IssueFixVersion::getFixVersion, LongStringPair::setValue));
    }
}
