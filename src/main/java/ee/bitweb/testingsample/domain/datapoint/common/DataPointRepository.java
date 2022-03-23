package ee.bitweb.testingsample.domain.datapoint.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPointRepository extends JpaRepository<DataPoint, Long>, JpaSpecificationExecutor<DataPoint> {}
