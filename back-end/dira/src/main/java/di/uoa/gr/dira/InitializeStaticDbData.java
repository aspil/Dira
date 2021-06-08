package di.uoa.gr.dira;

import di.uoa.gr.dira.configuration.spring.SpringProfiles;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Profile(SpringProfiles.NOT_TEST)
public class InitializeStaticDbData {
    private final DataSource dataSource;

    public InitializeStaticDbData(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(
                false,
                false,
                "UTF-8",
                new ClassPathResource("data.sql")
        );
        resourceDatabasePopulator.execute(dataSource);
    }
}