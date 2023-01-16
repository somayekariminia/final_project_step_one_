package ir.maktab.data.model.entity;

import ir.maktab.data.model.entity.Credit;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @Temporal(value=TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date RegistrationDate;

    @OneToOne
    private Credit credit;
}
