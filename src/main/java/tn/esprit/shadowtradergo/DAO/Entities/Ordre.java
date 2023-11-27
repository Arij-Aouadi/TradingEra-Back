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
public class Ordre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long idO ;
    String strategie ;
    String symbol ;
    @Enumerated(EnumType.STRING)
    TypeSymbol typeSymbol  ;
    @Enumerated(EnumType.STRING)
    TypeTransaction typetransaction ;
    int quantite;
    float prixLimite;
    float prixsousjacent ;
    @Enumerated(EnumType.STRING)
    TypeOrdre typeordre;
    String dureeValiditeOrdre;
    @Temporal(TemporalType.TIMESTAMP)
    Date dateOrdre;
    @Enumerated(EnumType.STRING)
    TypeStatut statut ;
    @JsonIgnore
    @ManyToOne
    Action action ;
    @JsonIgnore
    @ManyToOne
    Option option ;
    @ManyToMany
    @JsonIgnore
    List<Position> Achat;

    @ManyToMany
    @JsonIgnore
    List<Position> Vente;
    @ManyToOne
    @JsonIgnore
    Game game;

}
