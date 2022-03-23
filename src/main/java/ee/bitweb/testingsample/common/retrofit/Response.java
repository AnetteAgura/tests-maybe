package ee.bitweb.testingsample.common.retrofit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {

    private T data;
}
