package ir.maktab.data.model.entity;

import ir.maktab.data.model.enums.SpecialtyStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Expert extends Person {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    List<SubJob> servicesList = new ArrayList<>();

    @ToString.Exclude
    @OneToMany
    List<Review> listComment = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private SpecialtyStatus specialtyStatus;

    @Lob
    private byte[] expertImage;
}
