package ee.bitweb.testingsample.domain.datapoint;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;

import java.util.concurrent.TimeUnit;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockServerHelper {

    public static void  setupGetMockRouteWithString(
            ClientAndServer mockServer,
            String path,
            int code,
            int times,
            String response
    ){
        mockServer.when(
                request().withMethod("GET").withPath(path), Times.exactly(1)
        ).respond(
                response()
                        .withStatusCode(code).withDelay(new Delay(TimeUnit.MILLISECONDS, 500))
                        .withHeaders(
                                new Header("Content-Type", "application/json; charset=utf8")
                        )
                        .withBody(response)
        );

    }
}
