package ir.maktab.data.model.entity;

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
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating;
    private String comment;
    @ManyToMany
    List<Users> usersList = new ArrayList<>();
    @ManyToMany
    List<Expert> expertList = new ArrayList<>();
}
