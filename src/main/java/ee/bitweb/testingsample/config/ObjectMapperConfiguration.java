package ee.bitweb.testingsample.config;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ObjectMapperConfiguration {

    private final ObjectMapper mapper;

    @PostConstruct
    private void init() {
        this.mapper.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
    }
}
