package ee.bitweb.testingsample.domain.datapoint.features;


import ee.bitweb.testingsample.domain.datapoint.DataPointHelper;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.features.update.UpdateDataPointFeature;
import ee.bitweb.testingsample.domain.datapoint.features.update.UpdateDataPointModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UpdateDataPointsFeatureUnitTests {

    @InjectMocks
    private UpdateDataPointFeature updateDataPointFeature;

    @Mock
    PersistDataPointFeature persistDataPointFeature;

    @Captor
    private ArgumentCaptor<DataPoint> dataPointArgumentCaptor;
    @Captor
    private ArgumentCaptor<UpdateDataPointModel> dataPointModelArgumentCaptor;

    @Test
    void onUpdateDataPointModelShouldBeUpdated() throws Exception{
        UpdateDataPointModel dataPointModel = new UpdateDataPointModel(
                "external-id-1",
                "value-1",
                "comment-1",
                1
        );

        DataPoint point = DataPointHelper.create(1L);
        doReturn(point).when(persistDataPointFeature).save(point);
        updateDataPointFeature.update(point, dataPointModel);

        assertAll(
                () -> Assertions.assertEquals("external-id-1", point.getExternalId()),
                () -> Assertions.assertEquals("value-1", point.getValue()),
                () -> Assertions.assertEquals("comment-1", point.getComment()),
                () -> Assertions.assertEquals(1, point.getSignificance())
        );
    }
}
