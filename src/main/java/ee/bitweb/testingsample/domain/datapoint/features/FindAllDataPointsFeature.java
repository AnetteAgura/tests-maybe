package ee.bitweb.testingsample.domain.datapoint.features;

import java.util.List;

import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor

public class FindAllDataPointsFeature {




    private final DataPointRepository repository;

    public List<DataPoint> find() {
        return repository.findAll();
    }
}
