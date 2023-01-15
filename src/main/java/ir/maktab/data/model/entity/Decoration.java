package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.TypeDecoration;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Decoration")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Decoration extends Services {
    private TypeDecoration typeDecoration;
}
