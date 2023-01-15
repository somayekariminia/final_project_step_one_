package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.TypeCleaningAndHygiene;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CleaningAndHygiene")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CleaningAndHygiene extends Services {
    private TypeCleaningAndHygiene typeCleaningAndHygiene;
}
