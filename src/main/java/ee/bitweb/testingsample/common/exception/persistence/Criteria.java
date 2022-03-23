package ee.bitweb.testingsample.common.exception.persistence;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Criteria implements Serializable {

    private final String field;
    private final String value;
}
