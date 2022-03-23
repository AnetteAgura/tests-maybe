package ee.bitweb.testingsample.domain.datapoint.external;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ExternalServiceApi {

    @GET("/data-points")
    Call<List<DataPointResponse>> getAll();

    @Getter
    @Setter
    @NoArgsConstructor
    class DataPointResponse {

        private String externalId;
        private String value;
        private String comment;
        private Integer significance;
    }
}
