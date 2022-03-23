package ee.bitweb.testingsample.domain.datapoint.api.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.features.create.CreateDataPointModel;
import ee.bitweb.testingsample.domain.datapoint.features.update.UpdateDataPointModel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataPointMapper {

    public static CreateDataPointModel toCreateModel(DataPointPayload payload) {
        return new CreateDataPointModel(
                payload.getExternalId(),
                payload.getValue(),
                payload.getComment(),
                payload.getSignificance()
        );
    }

    public static UpdateDataPointModel toUpdateModel(DataPointPayload payload) {
        return new UpdateDataPointModel(
                payload.getExternalId(),
                payload.getValue(),
                payload.getComment(),
                payload.getSignificance()
        );
    }

    public static DataPointResponse toResponse(DataPoint point) {
        return new DataPointResponse(
                point.getId(),
                point.getExternalId(),
                point.getValue(),
                point.getComment(),
                point.getSignificance()
        );
    }

    public static List<DataPointResponse> toResponse(Collection<DataPoint> points) {
        return points.stream().map(DataPointMapper::toResponse).collect(Collectors.toList());
    }
}
