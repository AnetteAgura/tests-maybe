package ee.bitweb.testingsample.config;

import ee.bitweb.testingsample.common.trace.TraceIdFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TraceIdConfig {

    @Bean
    public TraceIdFilter traceIdFilter() {
        return new TraceIdFilter();
    }
}
