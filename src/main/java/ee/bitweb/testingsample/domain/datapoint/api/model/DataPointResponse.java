package ee.bitweb.testingsample.domain.datapoint.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DataPointResponse {
    private Long id;
    private String externalId;
    private String value;
    private String comment;
    private Integer significance;
}
