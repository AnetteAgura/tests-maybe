package ee.bitweb.testingsample.domain.datapoint.common;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Setter
@Getter
@ToString
public class DataPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String externalId;

    @Column(nullable = false)
    private String value;

    @Column
    private String comment;

    /**
     * Statistical weight of the data point used for calculations;
     * Negative number not allowed
     * 0 : Statistically insignificant;
     * 1 : Default value;
     * 2 : Statistically significant;
     */
    @Column(nullable = false)
    private Integer significance = 1;
}
