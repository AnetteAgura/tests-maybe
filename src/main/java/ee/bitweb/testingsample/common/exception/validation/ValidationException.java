package ee.bitweb.testingsample.common.exception.validation;

import java.util.Set;

import ee.bitweb.testingsample.common.exception.CoreException;

import lombok.Getter;

@Getter
public class ValidationException extends CoreException {

    private final Set<FieldError> errors;

    public ValidationException(Set<FieldError> errors) {
        this("Validation failed with errors", errors);
    }

    public ValidationException(String message, Set<FieldError> errors) {
        super(message);
        this.errors = errors;
    }
}
