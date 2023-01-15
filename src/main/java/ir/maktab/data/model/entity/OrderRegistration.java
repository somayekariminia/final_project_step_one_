package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.orderStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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
    private Date doWorkDate;
    private String address;
    private orderStatus orderStatus;
    @OneToOne
    private Services services;
}
