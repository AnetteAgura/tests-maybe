package ee.bitweb.testingsample.common.retrofit;

import java.io.IOException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import retrofit2.Call;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrofitRequestExecutor {

    private static final String REQUEST_ERROR = "REQUEST_ERROR";
    private static final String UNSUCCESSFUL_REQUEST_ERROR = "UNSUCCESSFUL_REQUEST_ERROR";
    private static final String EMPTY_RESPONSE_BODY_ERROR = "EMPTY_RESPONSE_BODY_ERROR";
/*
    public static <T> T execute(Call<ee.brais.core.retrofit.Response<T>> request) {
        retrofit2.Response<ee.brais.core.retrofit.Response<T>> response = doRequest(request);

        if (response.body().getData() == null)
            throw new RetrofitException(EMPTY_RESPONSE_BODY_ERROR, request, response);

        return response.body().getData();
    }
*/
    public static <T> T executeRaw(Call<T> request) {
        return doRequest(request).body();
    }

    private static <T> retrofit2.Response<T> doRequest(Call<T> request) {
        retrofit2.Response<T> response;
        try {
            response = request.execute();
        } catch (Exception e) {
            log.error("Request failed: ", e);

            throw new RetrofitException(REQUEST_ERROR, request);
        }

        validateResponse(response, request);

        return response;
    }

    private static <T> void validateResponse(retrofit2.Response<T> response, Call<T> request) {
        if (!response.isSuccessful()) {
            if (response.errorBody() != null) {
                try {
                    handleErrorBody(response, request);
                } catch (Exception e) {
                    if (e instanceof RetrofitException) {
                        throw (RetrofitException) e;
                    }
                    log.error("Failed to fetch error body", e);
                }
            }
            throw new RetrofitException(UNSUCCESSFUL_REQUEST_ERROR, request, response);
        }
    }

    private static <T> void handleErrorBody(retrofit2.Response<T> response, Call<T> request) throws IOException {
        if (response.errorBody() == null) return;
        var responseError = response.errorBody().string();

        log.error("Retrofit request failed {}", responseError);
        throw new RetrofitException(new JSONObject(responseError).toString(), request, response);
    }
}
