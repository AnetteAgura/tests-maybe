package ee.bitweb.testingsample.domain.datapoint.features;


import ee.bitweb.testingsample.domain.datapoint.DataPointHelper;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.features.create.CreateDataPointFeature;
import ee.bitweb.testingsample.domain.datapoint.features.create.CreateDataPointModel;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CreateDataPointFeatureUnitsTests {


    @InjectMocks
    private CreateDataPointFeature createDataPointFeature;


    @Mock
    private UpdateDataPointFeature updateDataPointFeature;

    @Captor
    private ArgumentCaptor<UpdateDataPointModel> updateDataPointModelArgumentCaptor;

    @Captor
    private ArgumentCaptor<DataPoint> dataPointArgumentCaptor;


    @Test
    void onValidDataPointModelShouldBeCreated() throws Exception {

        CreateDataPointModel dataPointModel = new CreateDataPointModel(
                "external-id-3",
                "value-3",
                "comment-3",
                3

        );

        DataPoint point = DataPointHelper.create(1L);
        doReturn(point).when(updateDataPointFeature).update(any(), any());
        createDataPointFeature.create(dataPointModel);

        assertAll(
                () -> Assertions.assertEquals("external-id-1", point.getExternalId()),
                () -> Assertions.assertEquals("some-value-1", point.getValue()),
                () -> Assertions.assertEquals("some-comment-1", point.getComment()),
                () -> Assertions.assertEquals(1, point.getSignificance())
        );

    }

    @Test
    void onValidDAtaModelShouldSaveAndReturn() throws Exception {
        CreateDataPointModel dataPointModel = new CreateDataPointModel(
                "external-id-1",
                "value-1",
                "comment-1",
                1

        );

        DataPoint point = DataPointHelper.create(1L);
        createDataPointFeature.create(dataPointModel);
        verify(updateDataPointFeature, times(1)).update(dataPointArgumentCaptor.capture(), updateDataPointModelArgumentCaptor.capture());


        assertAll(
                () -> Assertions.assertEquals("value-1", updateDataPointModelArgumentCaptor.getValue().getValue()),
                () -> Assertions.assertNull(dataPointArgumentCaptor.getValue().getValue()),
                () -> Assertions.assertEquals("comment-1", updateDataPointModelArgumentCaptor.getValue().getComment()),
                () -> Assertions.assertNull(dataPointArgumentCaptor.getValue().getComment()),
                () -> Assertions.assertEquals(1, updateDataPointModelArgumentCaptor.getValue().getSignificance()),
                () -> Assertions.assertEquals(1, dataPointArgumentCaptor.getValue().getSignificance()),
                () -> Assertions.assertEquals("external-id-1", updateDataPointModelArgumentCaptor.getValue().getExternalId()),
                () -> Assertions.assertNull(dataPointArgumentCaptor.getValue().getExternalId())
        );

    }

}

