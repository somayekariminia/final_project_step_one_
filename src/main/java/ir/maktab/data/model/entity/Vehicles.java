package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.TypeVehicle;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("Vehicles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Vehicles extends Services{
    private TypeVehicle typeVehicle;
}
