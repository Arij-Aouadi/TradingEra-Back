package tn.esprit.shadowtradergo.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    Long idO ;
    String strategie ;
    String symbol ;
    ////zyeda
    String nom;
    @Enumerated(EnumType.STRING)
    TypeSymbol typeSymbol  ;
    @Enumerated(EnumType.STRING)
    TypeTransaction typetransaction ;
    int quantite;
    float prixOrdre;
    float prixsousjacent ;
    float prixStop;
    float prixProfit ;
    @Enumerated(EnumType.STRING)
    TypeOrdre typeordre;     ////(yaaaani , "Market Order," "Limit Order," "Stop Order")
    String dureeValiditeOrdre;
    Date dateOrdre;
    float prixLimite ;
    ///
    Date dateAchatVente;
    @Enumerated(EnumType.STRING)
    TypeStatut statut ;
    /// l option zeyedstrike w expiration date
    @JsonIgnore
    @JsonProperty("idAction")

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAction")

    Action action ;
    @JsonIgnore
    @ManyToOne
    Option option ;
    @ManyToMany
    @JsonIgnore
    List<Position> achat;

    @ManyToMany
    @JsonIgnore
    List<Position> vente;
}
