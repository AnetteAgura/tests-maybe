package ee.bitweb.testingsample.domain.datapoint.external;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Component
@Validated
@ConfigurationProperties(prefix = "data-points.external")
public class ExternalServiceProperties {

    @NotBlank
    private String baseUrl;
}
