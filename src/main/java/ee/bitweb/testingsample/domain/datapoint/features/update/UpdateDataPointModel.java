package ee.bitweb.testingsample.domain.datapoint.features.update;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public class UpdateDataPointModel {

    private String externalId;
    private String value;
    private String comment;
    private Integer significance;
}
