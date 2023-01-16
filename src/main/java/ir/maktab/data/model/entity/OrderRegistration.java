package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.orderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class OrderRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal offerPrice;
    private String aboutWork;

    @Temporal(value = TemporalType.DATE)//view
    private Date doWorkDate;

    @Temporal(value=TemporalType.DATE)
    private Date startDoWorkDate;

    private String address;
    private orderStatus orderStatus;

    @OneToOne
    private SubJob subJob;

    @OneToMany
    List<Offers> offersList=new ArrayList<>();

}
