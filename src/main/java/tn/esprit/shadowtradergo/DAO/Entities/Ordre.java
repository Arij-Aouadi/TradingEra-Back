package tn.esprit.shadowtradergo.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

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
    int idO ;
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
    Date dateOrdre;
    @Enumerated(EnumType.STRING)
    TypeStatut statut ;
    @ManyToOne
    Action action ;
    @ManyToOne
    Option option ;
    @JsonIgnore
    @ManyToMany
    List<Position> Achat;
    @JsonIgnore
    @ManyToMany
    List<Position> Vente;
}
