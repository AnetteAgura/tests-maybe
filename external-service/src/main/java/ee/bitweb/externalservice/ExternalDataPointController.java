package ee.bitweb.externalservice;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("Simulator")
@RequestMapping(ExternalDataPointController.BASE_URL)
public class ExternalDataPointController {

    public static final String BASE_URL = "/data-points";

    @GetMapping
    public List<DataPointResponse> list() {
        return List.of(
                create(1L),
                create(2L),
                create(3L)
        );
    }

    private DataPointResponse create(Long id) {
        return new DataPointResponse(
                "external-id-" + id,
                UUID.randomUUID().toString(),
                "comment " + UUID.randomUUID(),
                (int) (id % 2)
        );
    }

    @Getter
    @AllArgsConstructor
    public static class DataPointResponse {

        private String externalId;
        private String value;
        private String comment;
        private Integer significance;
    }
}
