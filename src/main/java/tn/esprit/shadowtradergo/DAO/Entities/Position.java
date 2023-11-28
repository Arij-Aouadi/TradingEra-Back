package tn.esprit.shadowtradergo.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    double plusOuMoinsValue;
    Double variation;
    String statusPosition ;

    @JsonIgnore
    @ManyToMany(mappedBy = "achat")
    List<Ordre> achat;
    @JsonIgnore
    @ManyToMany(mappedBy = "vente")
    List<Ordre> vente ;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    User user;
    @Override
    public int hashCode() {
        return Objects.hash(idP); // Utilisez uniquement l'ID pour éviter la récursion
    }

}
