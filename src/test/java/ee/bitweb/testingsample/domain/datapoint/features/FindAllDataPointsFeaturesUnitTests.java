package ee.bitweb.testingsample.domain.datapoint.features;


import ee.bitweb.testingsample.domain.datapoint.DataPointHelper;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FindAllDataPointsFeaturesUnitTests {


    @InjectMocks
    private FindAllDataPointsFeature findAllDataPointsFeature;

    @Mock
    private DataPointRepository repository;


    @Test
    void onFindingShouldReturnArrayOfDataPoints() {
        DataPoint firstPoint = DataPointHelper.create(1L);
        DataPoint secondPoint = DataPointHelper.create(2L);

        doReturn(List.of(firstPoint, secondPoint)).when(repository).findAll();
        List<DataPoint> dataPoints = findAllDataPointsFeature.find();

        assertAll(
                () -> assertEquals(2L, dataPoints.size()),
                () -> assertEquals("external-id-1", dataPoints.get(0).getExternalId()),
                () -> Assertions.assertEquals("some-value-1", dataPoints.get(0).getValue()),
                () -> Assertions.assertEquals("some-comment-1", dataPoints.get(0).getComment()),
                () -> Assertions.assertEquals(1, dataPoints.get(0).getSignificance()),
                () -> assertEquals("external-id-2", dataPoints.get(1).getExternalId()),
                () -> Assertions.assertEquals("some-value-2", dataPoints.get(1).getValue()),
                () -> Assertions.assertEquals("some-comment-2", dataPoints.get(1).getComment()),
                () -> Assertions.assertEquals(0, dataPoints.get(1).getSignificance())

        );

    }
}
