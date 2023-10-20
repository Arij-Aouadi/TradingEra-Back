package tn.esprit.shadowtradergo.DAO.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ordre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    int idO ;
    String strategie ;
    String symbol ;
    TypeSymbol typeSymbol  ;
    TypeTransaction typetransaction ;
    int quantite;
    float prixLimite;
    float prixsousjacent ;
    TypeOrdre typeordre;
    String dureeValiditeOrdre;
    Date dateOrdre;
    TypeStatut statut ;
}
