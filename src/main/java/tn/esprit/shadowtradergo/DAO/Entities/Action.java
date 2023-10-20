package tn.esprit.shadowtradergo.DAO.Entities ;

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

public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    long id;
    @OneToMany(mappedBy = "actionOrdre")
    List<Ordre> ordres;
    @OneToMany(mappedBy = "actionOption")
    List<Option> options;
}
