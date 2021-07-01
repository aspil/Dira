package di.uoa.gr.dira.configuration.modelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A component class that will retrieve all property mappings.
 * With that class you can configure model mappers
 */
@Component
public class ModelMapperConfigurator {
    private final List<? extends IMapConfiguration> modelConfigurations;

    public ModelMapperConfigurator(List<? extends IMapConfiguration> modelConfigurations) {
        this.modelConfigurations = modelConfigurations;
    }

    /**
     * Configure a model mapper with all the available property mappings
     * implemented
     *
     * @param mapper The mapper to configure
     */
    public void configure(ModelMapper mapper) {
        modelConfigurations.forEach(conf -> conf.configure(mapper));
    }
}
