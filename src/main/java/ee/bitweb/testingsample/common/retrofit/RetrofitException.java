package ee.bitweb.testingsample.common.retrofit;

import ee.bitweb.testingsample.common.exception.CoreException;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import retrofit2.Call;

@Getter
public class RetrofitException extends CoreException {

    private HttpStatus httpStatus;
    private String url;
    private String errorBody;

    public RetrofitException(String message) {
        super(message);
        this.errorBody = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public <T> RetrofitException(String message, Call<T> request, retrofit2.Response<T> response) {
        super(message);
        mapRequestFields(request);
        this.errorBody = message;
        this.httpStatus = response != null ? HttpStatus.resolve(response.code()) : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public <T> RetrofitException(String message, Call<T> request) {
        super(message);
        this.errorBody = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        mapRequestFields(request);
    }

    public <T> void mapRequestFields(Call<T> request) {
        if (request != null && request.request() != null && request.request().url() != null) {
            this.url = request.request().url().toString();
        }
    }
}
