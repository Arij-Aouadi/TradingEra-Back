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
    String name;

    @Temporal(TemporalType.DATE)
    Date startDate;

    @Temporal(TemporalType.DATE)
    Date endDate;

    float initialBalance;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    List<Ordre> orderList;

    public List<Ordre> getOrdres() {
        return this.orderList;}

}
