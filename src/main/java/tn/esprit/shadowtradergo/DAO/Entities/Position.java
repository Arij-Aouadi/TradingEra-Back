package tn.esprit.shadowtradergo.DAO.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long idP ;
    Long quantité ;
    Long prixdumarché ;
    Long prixfinale ;
    Long PPNR ;
    Long prixmoyen ;
}
