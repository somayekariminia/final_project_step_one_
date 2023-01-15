package ir.maktab.data.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    private BigDecimal offerPriceByExpert;
    private Date submitAnOfferDate;
    private int  DurationOfWork;
    @OneToOne
    private Expert expert;
    @OneToMany
    List<OrderRegistration> orderRegistrationList=new ArrayList<>();
}
