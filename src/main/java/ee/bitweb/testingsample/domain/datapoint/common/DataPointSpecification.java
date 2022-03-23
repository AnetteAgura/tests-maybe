package ee.bitweb.testingsample.domain.datapoint.common;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataPointSpecification {

    public static Specification<DataPoint> id(Long id) {
        return (root, query, builder) -> builder.equal(root.get(DataPoint_.id), id);
    }

    public static Specification<DataPoint> externalId(String id) {
        return (root, query, builder) -> builder.equal(root.get(DataPoint_.externalId), id);
    }
}
