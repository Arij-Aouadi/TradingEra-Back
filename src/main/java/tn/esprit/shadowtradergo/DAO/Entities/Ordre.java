package tn.esprit.shadowtradergo.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    float prixOrdre;
    float prixsousjacent ;
    float prixStop;

    float prixProfit ;

    float profitandloss;

    float prixLimite ;


    @Enumerated(EnumType.STRING)
    TypeOrdre typeordre;
    String dureeValiditeOrdre;
    @Temporal(TemporalType.TIMESTAMP)
    Date dateOrdre;

    private int quantiteAchat;
    private int quantiteVente;

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
    List<Position> achat;

    @ManyToMany
    @JsonIgnore
    List<Position> vente;
    @ManyToOne
    @JsonIgnore
    Game game;

    @JsonIgnore
    @OneToMany(mappedBy = "ordre", cascade = CascadeType.ALL)
    Set<Historique> historiques;


    private void recalculerQuantite() {
        this.quantite = quantiteAchat + quantiteVente;
    }

}
