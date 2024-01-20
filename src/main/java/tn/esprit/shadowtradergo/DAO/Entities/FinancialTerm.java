package tn.esprit.shadowtradergo.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name= "FinancialTerm")
public class FinancialTerm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String term;
    private String definition;
    private String imageUrl; // Ajoutez ce champ pour stocker l'URL de l'image
    private String videoUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;


    // Autres champs pertinents

    // Getters et setters
}
