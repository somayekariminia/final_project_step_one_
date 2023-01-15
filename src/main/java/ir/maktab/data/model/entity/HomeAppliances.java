package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.TYpeHomeAppliances;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("HomeAppliances")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class HomeAppliances  extends Services{
    private TYpeHomeAppliances tYpeHomeAppliances;
}
