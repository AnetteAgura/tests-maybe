package ee.bitweb.testingsample.domain.datapoint.features;

import java.util.ArrayList;
import java.util.List;

import ee.bitweb.testingsample.common.exception.persistence.EntityNotFoundException;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.external.ExternalService;
import ee.bitweb.testingsample.domain.datapoint.external.ExternalServiceApi;
import ee.bitweb.testingsample.domain.datapoint.features.create.CreateDataPointFeature;
import ee.bitweb.testingsample.domain.datapoint.features.create.CreateDataPointModel;
import ee.bitweb.testingsample.domain.datapoint.features.update.UpdateDataPointFeature;
import ee.bitweb.testingsample.domain.datapoint.features.update.UpdateDataPointModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportDataPointsFeature {

    private final UpdateDataPointFeature updateFeature;
    private final CreateDataPointFeature createFeature;
    private final GetDataPointByExternalIdFeature getDataPointByExternalIdFeature;
    private final ExternalService externalService;

    public List<DataPoint> execute() {
        log.info("Starting to import data points from external service");

        List<ExternalServiceApi.DataPointResponse> response = externalService.getAll();

        log.info("Got a result of size: {}", response.size());

        List<DataPoint> result = new ArrayList<>();

        for (ExternalServiceApi.DataPointResponse element : response) {
            DataPoint dataPoint;
            try {
                dataPoint = updateFeature.update(
                        getDataPointByExternalIdFeature.get(element.getExternalId()),
                        toUpdateModel(element)
                );
            } catch (EntityNotFoundException e) {
                log.info("data point with external id {} not found, creating new", element.getExternalId());
                dataPoint = createFeature.create(toCreateModel(element));
            }
            result.add(dataPoint);
        }

        return result;
    }

    private CreateDataPointModel toCreateModel(ExternalServiceApi.DataPointResponse model) {
        return new CreateDataPointModel(
                model.getExternalId(),
                model.getValue(),
                model.getComment(),
                model.getSignificance()
        );
    }

    private UpdateDataPointModel toUpdateModel(ExternalServiceApi.DataPointResponse model) {
        return new UpdateDataPointModel(
                model.getExternalId(),
                model.getValue(),
                model.getComment(),
                model.getSignificance()
        );
    }
}
