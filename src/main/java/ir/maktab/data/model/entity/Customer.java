package ir.maktab.data.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Customer extends Person{
    @OneToMany
    private List<OrderRegistration> orderRegistrationList=new ArrayList<>();
}
