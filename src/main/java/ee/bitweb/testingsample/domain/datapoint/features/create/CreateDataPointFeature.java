package ee.bitweb.testingsample.domain.datapoint.features.create;

import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.features.PersistDataPointFeature;
import ee.bitweb.testingsample.domain.datapoint.features.update.UpdateDataPointFeature;
import ee.bitweb.testingsample.domain.datapoint.features.update.UpdateDataPointModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateDataPointFeature {

    private final UpdateDataPointFeature updateDataPointFeature;

    public DataPoint create(CreateDataPointModel model) {
        log.info("Creating new Data Point from model {}", model);

        return updateDataPointFeature.update(new DataPoint(), toUpdateModel(model));
    }

    private UpdateDataPointModel toUpdateModel(CreateDataPointModel model) {
        return new UpdateDataPointModel(
                model.getExternalId(),
                model.getValue(),
                model.getComment(),
                model.getSignificance()
        );
    }
}
