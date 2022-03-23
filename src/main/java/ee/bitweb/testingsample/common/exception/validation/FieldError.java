package ee.bitweb.testingsample.common.exception.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class FieldError {

    private final String field;

    private final String reason;

    private final String message;
}
