package tn.esprit.shadowtradergo.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name= "Action")

public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long idAction;
    private String symbole;
    private String name;
   private Double coursActuel;// ca sera une methode
    private Float variationEnPorcentage;
    private Float  ouverture;
    private Float haut;
    private Float  bas;
    private Float dernier;
    private Float  volume;
    @OneToMany(mappedBy ="action")
    @JsonIgnore
        List<Ordre> ordreList ;
    @OneToMany(mappedBy ="action1")
            @JsonIgnore
    List<Option> optionList ;

}
