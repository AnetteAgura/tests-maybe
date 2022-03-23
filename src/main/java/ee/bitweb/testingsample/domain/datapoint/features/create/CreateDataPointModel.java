package ee.bitweb.testingsample.domain.datapoint.features.create;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public class CreateDataPointModel {

    private String externalId;
    private String value;
    private String comment;
    private Integer significance;
}
