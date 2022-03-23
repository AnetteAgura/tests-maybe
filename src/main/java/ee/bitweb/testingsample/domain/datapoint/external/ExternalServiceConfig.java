package ee.bitweb.testingsample.domain.datapoint.external;

import ee.bitweb.testingsample.common.retrofit.RetrofitBuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExternalServiceConfig {

    private final ExternalServiceProperties properties;

    @Bean
    public ExternalServiceApi externalServiceApi() {
        return RetrofitBuilder.createApiService(properties.getBaseUrl(), ExternalServiceApi.class);
    }
}
