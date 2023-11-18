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
@Table(name= "FinancialTerm")
public class FinancialTerm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String term;
    private String definition;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;
    @ElementCollection
    @CollectionTable(name = "financial_term_photos", joinColumns = @JoinColumn(name = "financial_term_id"))
    @Column(name = "photo_url")
    private List<String> photos;

    @Lob
    @Column(name = "statistics", length = Integer.MAX_VALUE)
    private String statistics;

    @Lob
    @Column(name = "examples", length = Integer.MAX_VALUE)
    private String examples;

    // Autres champs pertinents

    // Getters et setters
}
