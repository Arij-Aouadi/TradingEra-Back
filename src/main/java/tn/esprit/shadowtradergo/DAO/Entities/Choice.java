package tn.esprit.shadowtradergo.DAO.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name= "Choice")
public class Choice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )

    private Long id;
    private String content; // Contenu du choiX
    private boolean correct; // Indique si le choix est correct ou non

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}


