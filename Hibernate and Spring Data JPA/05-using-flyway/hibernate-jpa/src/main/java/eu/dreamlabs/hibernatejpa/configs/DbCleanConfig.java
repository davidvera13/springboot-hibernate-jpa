package eu.dreamlabs.hibernatejpa.configs;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("clean")
@Configuration
public class DbCleanConfig {
    @Bean
    public FlywayMigrationStrategy cleanStrategy() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}
