package ee.bitweb.testingsample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;


/**
 ### Testcontainers configuration
 Add the following line to `~/.testcontainers.properties`. This allows reuse of testcontainers and thus significantly
 improves the speed of initiating tests. Also, newly added testcontainer definitions should have reuse enabled unless
 there is a very good reason not to do so.
 testcontainers.reuse.enable=true
 */

@Slf4j
@Configuration
public class TestContainerConfiguration {
    public static PostgreSQLContainer<?> postgres;

    static {
        setupPostgres();
    }

    private static void setupPostgres() {
        log.info("Setting up Postgres for tests");
        postgres = new PostgreSQLContainer<>("postgres:13.2-alpine")
                .withReuse(true)
                .withDatabaseName("testing-sample-test");

        postgres.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }
}
