package di.uoa.gr.dira.configuration.modelMapper;


import di.uoa.gr.dira.entities.sprint.Sprint;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.models.sprint.SprintModel;
import di.uoa.gr.dira.util.mapper.ListConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class SprintMapperConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        configureSprintEntityToSprintModel(mapper);
    }

    /**
     * Creates the model mapper configuration when mapping a Sprint entity to a Sprint Model
     *
     * @param mapper The mapper to configure
     */
    private void configureSprintEntityToSprintModel(ModelMapper mapper) {
        TypeMap<Sprint, SprintModel> typeMap = mapper.createTypeMap(Sprint.class, SprintModel.class);
        typeMap.addMappings(mapping -> mapping.using(ListConverter.withMapper(mapper, IssueModel.class))
                .map(Sprint::getIssues, SprintModel::setIssueModels)
        );
    }
}
