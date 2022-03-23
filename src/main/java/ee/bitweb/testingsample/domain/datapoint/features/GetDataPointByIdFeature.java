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
public class GetDataPointByIdFeature {

    private final DataPointRepository repository;

    public DataPoint get(Long id) {
        log.info("Request user by id: {}", id);

        return repository.findOne(DataPointSpecification.id(id)).orElseThrow(() -> createException(id));
    }

    private EntityNotFoundException createException(Long id) {
        return new EntityNotFoundException(
                DataPoint.class.getSimpleName(),
                Set.of(
                        new Criteria(
                                DataPoint_.ID,
                                String.valueOf(id)
                        )
                )
        );
    }
}
