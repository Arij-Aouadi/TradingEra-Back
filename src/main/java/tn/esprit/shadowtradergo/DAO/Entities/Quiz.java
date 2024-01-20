package tn.esprit.shadowtradergo.DAO.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name= "Quiz")
public class Quiz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Enumerated(EnumType.STRING)
    TypeQuiz typeQuiz ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    @JsonIgnore
    private List<Question> questions;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "time_limit")
    private Integer timeLimit;
    // Autres propriétés et méthodes
    public static final int DEFAULT_TIME_LIMIT = 5000; // 300 secondes (5 minutes)

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "quiz")
    private List<UserAnswer> userAnswers;
    public List<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

}
