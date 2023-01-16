package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.SpecialtyStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Expert extends Person{
    private SpecialtyStatus specialtyStatus;
    private int rating;
    @Lob
    private byte[] expertImage;
}
