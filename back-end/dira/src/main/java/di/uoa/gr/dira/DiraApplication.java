package di.uoa.gr.dira;

import di.uoa.gr.dira.configuration.modelMapper.ModelMapperConfigurator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DiraApplication {
    @Autowired
    @Bean
    public ModelMapper modelMapperSingleton(ModelMapperConfigurator configurator) {
        ModelMapper mapper = new ModelMapper();
        configurator.configure(mapper);
        return mapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(DiraApplication.class, args);
    }
}
