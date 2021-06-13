package di.uoa.gr.dira.configuration;

import di.uoa.gr.dira.configuration.modelMapper.*;
import di.uoa.gr.dira.configuration.spring.SpringProfiles;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile(SpringProfiles.TEST)
public class ModelMapperConfiguration {
    private final List<IMapConfiguration> configurations = new ArrayList<IMapConfiguration>() {{
            add(new CustomerMapperConfiguration());
            add(new ProjectMapperConfiguration());
            add(new IssueMapperConfiguration());
        }};

    private final ModelMapperConfigurator configurator = new ModelMapperConfigurator(configurations);

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        configurator.configure(mapper);
        return mapper;
    }
}
