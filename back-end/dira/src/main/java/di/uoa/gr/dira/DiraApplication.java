package di.uoa.gr.dira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiraApplication.class, args);
	}

}
