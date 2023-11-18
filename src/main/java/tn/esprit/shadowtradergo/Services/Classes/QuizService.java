package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Quiz;
import tn.esprit.shadowtradergo.DAO.Repositories.QuizRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    public Quiz getQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElse(null);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }
    public Quiz getQuizWithQuestions(Long quizId) {
        return quizRepository.findById(quizId).orElse(null);
    }

}
