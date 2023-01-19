package ir.maktab.data.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Customer extends Person {
}
