package tn.esprit.shadowtradergo.DAO.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
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
@Table(name= "User")
public class Option implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    long id;
    float prixExerciceOption;
    @Temporal(TemporalType.DATE )
    Date DateExpirationOption;
    float ouvreture ;
    float  haut;
    float bas ;
    float  dernier ;
    float volume ;
    @Enumerated(EnumType.STRING)
    Etat etat;


}
