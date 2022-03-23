package ee.bitweb.testingsample.domain.datapoint.external;

import java.util.List;

import ee.bitweb.testingsample.common.retrofit.RetrofitRequestExecutor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalService {

    private final ExternalServiceApi api;

    public List<ExternalServiceApi.DataPointResponse> getAll() {
        log.info("Requesting all data points from external service");

        return RetrofitRequestExecutor.executeRaw(api.getAll());
    }
}
