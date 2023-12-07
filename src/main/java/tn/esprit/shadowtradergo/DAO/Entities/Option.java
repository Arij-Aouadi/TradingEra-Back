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
@Table(name= "Option")
public class Option implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    long idOption;
    float prixExerciceOption;
    @Temporal(TemporalType.DATE )
    Date DateExpirationOption;
    float ouvreture ;
    float  haut;
    float bas ;
    float  dernier ;
    float volume ;
    float prime ;
 //   @Enumerated(EnumType.STRING)
   // Etat etat;
    @OneToMany(mappedBy ="option")
    @JsonIgnore

    List<Ordre> ordreOption ;
    @ManyToOne
            @JsonIgnore
    Action action1 ;


}
