package tn.esprit.shadowtradergo.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
    String symbole;
    String nom;
    Long quantité;

    Long valeurActuelle;
    Date dateAchatVente;
    Long prixAchat;
    Long plusOuMoinsValue;
    Double variation;

    @JsonIgnore
    @ManyToMany(mappedBy = "achat")
    List<Ordre> achat;
    @JsonIgnore
    @ManyToMany(mappedBy = "vente")
    List<Ordre> vente ;
    @OneToOne(mappedBy = "position")
    @JsonIgnore
     User user ;
}
