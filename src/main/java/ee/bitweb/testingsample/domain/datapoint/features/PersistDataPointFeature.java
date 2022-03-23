package ee.bitweb.testingsample.domain.datapoint.features;

import ee.bitweb.testingsample.common.exception.persistence.ConflictException;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointRepository;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint_;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersistDataPointFeature {

    private final DataPointRepository repository;

    public DataPoint save(DataPoint point) {
        log.info("Persisting DataPoint: {}", point);
        try {
            return repository.save(point);
        } catch (DataIntegrityViolationException e) {
            log.error("Error persisting {} as external id is not unique", point);

            throw new ConflictException(
                    "Cannot persist data point as external id already exists",
                    DataPoint.class.getSimpleName(),
                    DataPoint_.EXTERNAL_ID,
                    point.getExternalId()
            );
        }
    }
}
