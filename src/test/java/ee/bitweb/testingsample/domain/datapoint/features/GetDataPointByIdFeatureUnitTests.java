package ee.bitweb.testingsample.domain.datapoint.features;


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
import ee.bitweb.testingsample.common.exception.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class GetDataPointByIdFeatureUnitTests {

    @InjectMocks
    private GetDataPointByIdFeature getDataPointByIdFeature;


    @Mock
    private DataPointRepository repository;

    @Test
    void onSuccessShouldReturnOneDatapointByExternalId() throws Exception{
        DataPoint point = DataPointHelper.create(1L);

        repository.save(point);

        doReturn(Optional.of(point)).when(repository).findOne(DataPointSpecification.id(any()));
        getDataPointByIdFeature.get(1L);

        assertAll(
                ()-> Assertions.assertEquals("external-id-1", point.getExternalId()),
                ()-> Assertions.assertEquals("some-value-1", point.getValue()),
                ()-> Assertions.assertEquals("some-comment-1", point.getComment()),
                ()-> Assertions.assertEquals(1, point.getSignificance())
        );
    }

    @Test
    void onInvalidIdShouldThrowEntityNotFoundException() throws Exception{
        DataPoint point = DataPointHelper.create(1L);

        repository.save(point);

        assertThrows(EntityNotFoundException.class, () ->{
            getDataPointByIdFeature.get(1L);
        });
    }

}