package di.uoa.gr.dira;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DiraApplication {
    @Bean
    public ModelMapper modelMapperSingleton() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(DiraApplication.class, args);
    }

}
