package tn.esprit.shadowtradergo.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name= "Historique")
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long idhistorique;
  String symbole ;
    int quantity;

  @Enumerated(EnumType.STRING)
  TypeSymbol typeSymbol  ;
   // float prixOrdre;
//    float prixsousjacent ;
  //  float prixStop;
   // float prixProfit ;
 Double profitandloss;
   Double rendement;
   String nomGame;
  @Enumerated(EnumType.STRING)
  TypeTransaction typeTransaction;
    @Enumerated(EnumType.STRING)
    TypeOrdre typeordre;

    @JsonIgnore
    @ManyToOne
    User user;
    @JsonIgnore
    @ManyToOne
    Ordre ordre;
}

