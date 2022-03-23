package ee.bitweb.testingsample.domain.datapoint.features;

import ee.bitweb.testingsample.common.exception.persistence.EntityNotFoundException;
import ee.bitweb.testingsample.domain.datapoint.DataPointHelper;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointRepository;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class GetDataPointByExternalIdFeatureUnitTests {

    @InjectMocks
    GetDataPointByExternalIdFeature getDataPointByExternalIdFeature;

    @Mock
    private DataPointRepository repository;

    @Test
    void onValidIdShouldReturnOneDatapoint() throws Exception {
        DataPoint point = DataPointHelper.create(1L);

        doReturn(Optional.of(point)).when(repository).findOne(DataPointSpecification.id(any()));
        getDataPointByExternalIdFeature.get("external-id-1");

        assertAll(
                () -> Assertions.assertEquals("external-id-1", point.getExternalId()),
                () -> Assertions.assertEquals("some-value-1", point.getValue()),
                () -> Assertions.assertEquals("some-comment-1", point.getComment()),
                () -> Assertions.assertEquals(1, point.getSignificance())
        );
    }

    @Test
    void onInvalidIdShouldThrowEntityNotFoundException() throws Exception {
        assertThrows(EntityNotFoundException.class, () -> {
            getDataPointByExternalIdFeature.get("external-id-1");
        });

    }
}
