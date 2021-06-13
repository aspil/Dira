package di.uoa.gr.dira.configuration.modelMapper.entities.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.IssueLink;
import di.uoa.gr.dira.models.issue.IssueLinkModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class IssueLinkModeMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<IssueLink, IssueLinkModel> typeMap = mapper.createTypeMap(IssueLink.class, IssueLinkModel.class);
        typeMap.addMappings(m -> m.using(ctx -> {
            IssueLink link = (IssueLink) ctx.getSource();
            switch (link.getLinkType()) {
                case DEPENDS_ON: return "Depends on";
                case RELATES_TO: return "Relates to";
                default: return "Unknown link";
            }
        }).map(IssueLink::getLinkType, IssueLinkModel::setLinkType));

        typeMap.addMappings(m -> m.map(IssueLink::getLinkedIssue, IssueLinkModel::setLinkedIssue));
    }
}
