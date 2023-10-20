package tn.esprit.shadowtradergo.DAO.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    long id;
    @ManyToOne
    Action actionOption;
    @OneToMany(mappedBy = "option")
    List<Ordre> ordres;
}
