package ee.bitweb.testingsample.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@EntityScan("ee.bitweb.testingsample.*")
@EnableJpaRepositories(value = "ee.bitweb.testingsample.*")
public class DataSourceConfig {

    @Bean("transactionManager")
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
}
