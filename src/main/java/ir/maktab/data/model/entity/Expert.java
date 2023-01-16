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
public class Expert extends Person{

    private SpecialtyStatus specialtyStatus;

    @Lob
    private byte[] expertImage;
    
    @ManyToMany
    List<SubJob> servicesList=new ArrayList<>();

    @OneToMany
    List<Review>listComment=new ArrayList<>();
}
