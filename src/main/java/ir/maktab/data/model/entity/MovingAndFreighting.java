package ir.maktab.data.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MovingAndFreighting")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MovingAndFreighting  extends Services{
}
