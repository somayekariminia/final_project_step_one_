package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.TypesFacilities;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Facilities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Facilities extends Services{
    private TypesFacilities typesFacilities;
}
