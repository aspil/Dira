package di.uoa.gr.dira.configuration.modelMapper.entities.issue;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.issue.*;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.util.mapper.ListConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class IssueModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<Issue, IssueModel> typeMap = mapper.createTypeMap(Issue.class, IssueModel.class);

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, IssueLabel.class))
                .map(Issue::getLabels, IssueModel::setLabels));

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, IssueComment.class))
                .map(Issue::getComments, IssueModel::setComments));

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, IssueFixVersion.class))
            .map(Issue::getFixVersions, IssueModel::setFixVersions));

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, IssueLink.class))
            .map(Issue::getIssueLinks, IssueModel::setIssueLinks));
    }
}
