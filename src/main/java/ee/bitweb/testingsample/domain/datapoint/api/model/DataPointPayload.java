package ee.bitweb.testingsample.domain.datapoint.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DataPointPayload {

    @NotBlank
    private String externalId;

    @NotBlank
    private String value;

    private String comment;

    @NotNull
    @Positive
    private Integer significance;
}
