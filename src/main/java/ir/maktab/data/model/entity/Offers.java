package ir.maktab.data.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    private BigDecimal offerPriceByExpert;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date submitAnOfferDate;

    Duration durationWork;

    @OneToOne
    private Expert expert;

}
