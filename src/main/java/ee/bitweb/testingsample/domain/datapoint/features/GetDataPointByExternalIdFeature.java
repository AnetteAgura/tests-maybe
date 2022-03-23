package ee.bitweb.testingsample.domain.datapoint.features;

import java.util.Set;

import ee.bitweb.testingsample.common.exception.persistence.Criteria;
import ee.bitweb.testingsample.common.exception.persistence.EntityNotFoundException;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointRepository;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointSpecification;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint_;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetDataPointByExternalIdFeature {

    private final DataPointRepository repository;

    public DataPoint get(String externalId) {
        return repository.findOne(DataPointSpecification.externalId(externalId)).orElseThrow(() -> createException(externalId));
    }

    private EntityNotFoundException createException(String id) {
        return new EntityNotFoundException(
                DataPoint.class.getSimpleName(),
                Set.of(
                        new Criteria(
                                DataPoint_.EXTERNAL_ID,
                                String.valueOf(id)
                        )
                )
        );
    }
}
