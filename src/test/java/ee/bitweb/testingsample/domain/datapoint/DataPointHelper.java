package ee.bitweb.testingsample.domain.datapoint;

import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataPointHelper {

    public static DataPoint create(Long id) {
        DataPoint p = new DataPoint();

        p.setExternalId("external-id-" + id);
        p.setValue("some-value-" + id);
        p.setComment("some-comment-" + id);
        p.setSignificance((int) (id % 2));

        return p;
    }
}
