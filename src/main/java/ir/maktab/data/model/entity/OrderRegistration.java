package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal offerPrice;
    private String aboutWork;

    @Column(unique = true)
    private String codeOrder;

    @Temporal(value = TemporalType.DATE)
    private Date doWorkDate;

    @OneToOne
    Address address;

    @Enumerated
    private OrderStatus orderStatus;

    @OneToOne
    private SubJob subJob;

    @OneToMany
    List<Offers> offersList = new ArrayList<>();

}
