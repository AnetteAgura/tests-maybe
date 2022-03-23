package ee.bitweb.testingsample.domain.datapoint.features.update;

import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.features.PersistDataPointFeature;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDataPointFeature {

    private final PersistDataPointFeature persistDataPointFeature;

    public DataPoint update(DataPoint point, UpdateDataPointModel model) {
        log.info("Updating data point {} with new state {}", point, model);

        point.setExternalId(model.getExternalId());
        point.setValue(model.getValue());
        point.setComment(model.getComment());
        point.setSignificance(model.getSignificance());

        return persistDataPointFeature.save(point);
    }
}
