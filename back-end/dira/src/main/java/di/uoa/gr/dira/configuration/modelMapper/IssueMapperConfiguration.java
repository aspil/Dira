package di.uoa.gr.dira.configuration.modelMapper;

import di.uoa.gr.dira.entities.issue.*;
import di.uoa.gr.dira.models.issue.IssueCreateModel;
import di.uoa.gr.dira.models.issue.IssueLinkModel;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.util.LongStringPair;
import di.uoa.gr.dira.util.mapper.ListConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class IssueMapperConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        configureIssueEntityToIssueModel(mapper);
        configureIssueModelToIssueEntity(mapper);
        configureIssueCreateModelToIssueEntity(mapper);
        configureIssueLinkEntityToIssueLinkModel(mapper);
        configureIssueLabelEntityToLongStringPair(mapper);
        configureIssueFixVersionEntityToLongStringPair(mapper);
        configureIssueCommentEntityToLongStringPair(mapper);
        configureLongStringPairToIssueCommentEntity(mapper);
        configureLongStringPairToIssueFixVersionEntity(mapper);
        configureLongStringPairToIssueLabelEntity(mapper);
    }

    /**
     * Creates a model mapper configuration when mapping an Issue entity to an IssueModel model
     *
     * @param mapper The mapper to configure
     */
    private void configureIssueEntityToIssueModel(ModelMapper mapper) {
        TypeMap<Issue, IssueModel> typeMap = mapper.createTypeMap(Issue.class, IssueModel.class);

        typeMap.addMappings(m -> m.map(s -> s.getAssignee().getName(), IssueModel::setAssignee));
        typeMap.addMappings(m -> m.map(s -> s.getReporter().getName(), IssueModel::setReporter));

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, LongStringPair.class))
                .map(Issue::getLabels, IssueModel::setLabels));

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, LongStringPair.class))
                .map(Issue::getComments, IssueModel::setComments));

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, LongStringPair.class))
                .map(Issue::getFixVersions, IssueModel::setFixVersions));

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, IssueLinkModel.class))
                .map(Issue::getIssueLinks, IssueModel::setIssueLinks));
    }

    /**
     * Creates a model mapper configuration when mapping an IssueModel to an IssueEntity
     *
     * @param mapper The mapper to configure
     */
    private void configureIssueModelToIssueEntity(ModelMapper mapper) {
        TypeMap<IssueModel, Issue> typeMap = mapper.createTypeMap(IssueModel.class, Issue.class);

        typeMap.addMappings(m -> m.skip(Issue::setId));
        typeMap.addMappings(m -> m.skip(Issue::setIssueLinks));

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, IssueLabel.class))
            .map(IssueModel::getLabels, Issue::setLabels)
        );

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, IssueFixVersion.class))
            .map(IssueModel::getFixVersions, Issue::setFixVersions)
        );

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, IssueComment.class))
            .map(IssueModel::getComments, Issue::setComments)
        );

        typeMap.addMappings(m -> m.using(ListConverter.withMapper(mapper, IssueLink.class))
            .map(IssueModel::getIssueLinks, Issue::setIssueLinks)
        );
    }

    /**
     * Creates a model mapper configuration when mapping an IssueCreateModel to an Issue entity
     *
     * @param mapper The mapper to configure
     */
    private void configureIssueCreateModelToIssueEntity(ModelMapper mapper) {
        TypeMap<IssueCreateModel, Issue> typeMap = mapper.createTypeMap(IssueCreateModel.class, Issue.class);
        typeMap.addMappings(m -> m.skip(Issue::setProject));
        typeMap.addMappings(m -> m.skip(Issue::setEpic));
        typeMap.addMappings(m -> m.skip(Issue::setAssignee));
        typeMap.addMappings(m -> m.skip(Issue::setReporter));
    }

    /**
     * Creates a model mapper configuration when mapping an IssueLink entity to an IssueLinkModel model
     *
     * @param mapper The mapper to configure
     */
    private void configureIssueLinkEntityToIssueLinkModel(ModelMapper mapper) {
        TypeMap<IssueLink, IssueLinkModel> typeMap = mapper.createTypeMap(IssueLink.class, IssueLinkModel.class);
        typeMap.addMappings(m -> m.using(ctx -> {
            IssueLink link = (IssueLink) ctx.getSource();
            switch (link.getLinkType()) {
                case DEPENDS_ON:
                    return "Depends on";
                case RELATES_TO:
                    return "Relates to";
                default:
                    return "Unknown link";
            }
        }).map(IssueLink::getLinkType, IssueLinkModel::setLinkType));

        typeMap.addMappings(m -> m.map(IssueLink::getLinkedIssue, IssueLinkModel::setLinkedIssue));
    }

    /**
     * Creates a model mapper configuration when mapping an IssueLabel entity to a LongStringPair
     *
     * @param mapper The mapper to configure
     */
    private void configureIssueLabelEntityToLongStringPair(ModelMapper mapper) {
        TypeMap<IssueLabel, LongStringPair> typeMap = mapper.createTypeMap(IssueLabel.class, LongStringPair.class);
        typeMap.addMappings(m -> m.map(IssueLabel::getId, LongStringPair::setKey));
        typeMap.addMappings(m -> m.map(IssueLabel::getName, LongStringPair::setValue));
    }

    /**
     * Creates a model mapper configuration when mapping an IssueFixVersion entity to a LongStringPair
     *
     * @param mapper The mapper to configure
     */
    private void configureIssueFixVersionEntityToLongStringPair(ModelMapper mapper) {
        TypeMap<IssueFixVersion, LongStringPair> typeMap = mapper.createTypeMap(IssueFixVersion.class, LongStringPair.class);
        typeMap.addMappings(m -> m.map(IssueFixVersion::getId, LongStringPair::setKey));
        typeMap.addMappings(m -> m.map(IssueFixVersion::getFixVersion, LongStringPair::setValue));
    }

    /**
     * Creates a model mapper configuration when mapping an IssueCommentEntity to a LongStringPair
     *
     * @param mapper The mapper to configure
     */
    private void configureIssueCommentEntityToLongStringPair(ModelMapper mapper) {
        TypeMap<IssueComment, LongStringPair> typeMap = mapper.createTypeMap(IssueComment.class, LongStringPair.class);
        typeMap.addMappings(m -> m.map(IssueComment::getId, LongStringPair::setKey));
        typeMap.addMappings(m -> m.map(IssueComment::getComment, LongStringPair::setValue));
    }

    /**
     * Creates a model mapper configuration when mapping a LongStringPair to an IssueLabel entity
     *
     * @param mapper The mapper to configure
     */
    private void configureLongStringPairToIssueLabelEntity(ModelMapper mapper) {
        TypeMap<LongStringPair, IssueLabel> typeMap = mapper.createTypeMap(LongStringPair.class, IssueLabel.class);
        typeMap.addMappings(m -> m.map(LongStringPair::getKey, IssueLabel::setId));
        typeMap.addMappings(m -> m.map(LongStringPair::getValue, IssueLabel::setName));
    }

    /**
     * Creates a model mapper configuration when mapping a LongStringPair to an IssueFixVersion entity
     *
     * @param mapper The mapper to configure
     */
    private void configureLongStringPairToIssueFixVersionEntity(ModelMapper mapper) {
        TypeMap<LongStringPair, IssueFixVersion> typeMap = mapper.createTypeMap(LongStringPair.class, IssueFixVersion.class);
        typeMap.addMappings(m -> m.map(LongStringPair::getKey, IssueFixVersion::setId));
        typeMap.addMappings(m -> m.map(LongStringPair::getValue, IssueFixVersion::setFixVersion));
    }

    /**
     * Creates a model mapper configuration when mapping a LongStringPair to an IssueComment entity
     *
     * @param mapper The mapper to configure
     */
    private void configureLongStringPairToIssueCommentEntity(ModelMapper mapper) {
        TypeMap<LongStringPair, IssueComment> typeMap = mapper.createTypeMap(LongStringPair.class, IssueComment.class);
        typeMap.addMappings(m -> m.map(LongStringPair::getKey, IssueComment::setId));
        typeMap.addMappings(m -> m.map(LongStringPair::getValue, IssueComment::setComment));
    }
}
