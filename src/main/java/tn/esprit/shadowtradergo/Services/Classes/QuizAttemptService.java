package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Quiz;
import tn.esprit.shadowtradergo.DAO.Entities.QuizAttempt;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.DAO.Repositories.QuizAttemptRepository;

import java.util.Date;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class QuizAttemptService {
    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    public QuizAttempt saveQuizAttempt(User user, Quiz quiz, int score) {
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setUser(user);
        quizAttempt.setQuiz(quiz);
        quizAttempt.setAttemptDate(new Date());
        quizAttempt.setScore(score);
        return quizAttemptRepository.save(quizAttempt);
    }
    public void updateQuizAttemptScore(Long quizAttemptId, int newScore) {
        QuizAttempt quizAttempt = quizAttemptRepository.findById(quizAttemptId).orElse(null);
        if (quizAttempt != null) {
            quizAttempt.setScore(newScore);
            quizAttemptRepository.save(quizAttempt);
        }
    }
}
