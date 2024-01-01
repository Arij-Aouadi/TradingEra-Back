package tn.esprit.shadowtradergo.DAO.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
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
@Table(name= "Game")

public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long idGame;
    String nomGame;
    String username ;
    int Rank ;
    double revenue;
    @ManyToOne
    User user;



}
