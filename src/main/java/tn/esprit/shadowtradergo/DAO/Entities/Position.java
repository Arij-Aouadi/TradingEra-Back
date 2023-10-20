package tn.esprit.shadowtradergo.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
    @JsonIgnore
    @ManyToMany(mappedBy = "Achat")
    List<Ordre> Achat;
    @JsonIgnore
    @ManyToMany(mappedBy = "Vente")
    List<Ordre> Vente ;
    @OneToOne(mappedBy = "position")
    @JsonIgnore
     User user ;
}
