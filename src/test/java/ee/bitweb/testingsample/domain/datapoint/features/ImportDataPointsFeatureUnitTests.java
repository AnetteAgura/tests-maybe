package ee.bitweb.testingsample.domain.datapoint.features;

import ee.bitweb.testingsample.common.exception.persistence.EntityNotFoundException;
import ee.bitweb.testingsample.domain.datapoint.DataPointHelper;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.external.ExternalService;
import ee.bitweb.testingsample.domain.datapoint.external.ExternalServiceApi;
import ee.bitweb.testingsample.domain.datapoint.features.create.CreateDataPointFeature;
import ee.bitweb.testingsample.domain.datapoint.features.create.CreateDataPointModel;
import ee.bitweb.testingsample.domain.datapoint.features.update.UpdateDataPointFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImportDataPointsFeatureUnitTests {


    @Mock
    private UpdateDataPointFeature updateFeature;


    @InjectMocks
    private ImportDataPointsFeature importDataPointsFeature;

    @Mock
    private CreateDataPointFeature createDataPointFeature;

    @Captor
    private ArgumentCaptor<CreateDataPointModel> dataPointArgumentCaptor;


    @Mock
    private GetDataPointByExternalIdFeature getDataPointByExternalIdFeature;

    @Mock
    private ExternalService externalService;

    @Test
    void onExecuteShouldReturnListDataPoints() {
        ExternalServiceApi.DataPointResponse response = new ExternalServiceApi.DataPointResponse();
        DataPoint dataPoint = DataPointHelper.create(1L);

        response.setExternalId("external-id-3");
        response.setComment("some-comment-3");
        response.setValue("some-value-3");
        response.setSignificance(1);

        doReturn(List.of(response)).when(externalService).getAll();
        doReturn(dataPoint).when(createDataPointFeature).create(any());

        doThrow(EntityNotFoundException.class).when(getDataPointByExternalIdFeature).get(anyString());
        importDataPointsFeature.execute();

        verify(createDataPointFeature, times(1)).create(dataPointArgumentCaptor.capture());

        assertAll(
                () -> assertEquals("external-id-3", dataPointArgumentCaptor.getValue().getExternalId()),
                () -> assertEquals("some-value-3", dataPointArgumentCaptor.getValue().getValue()),
                () -> assertEquals("some-comment-3", dataPointArgumentCaptor.getValue().getComment()),
                () -> assertEquals(1, dataPointArgumentCaptor.getValue().getSignificance())
        );

    }

}
