package tn.esprit.shadowtradergo.RestControllers;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.shadowtradergo.DAO.Entities.Quiz;
import tn.esprit.shadowtradergo.DAO.Entities.QuizAttempt;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.Services.Classes.QuizAttemptService;
import tn.esprit.shadowtradergo.Services.Classes.QuizService;
import tn.esprit.shadowtradergo.Services.Classes.UserService;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class QuizAttemptController {
    @Autowired
    private QuizAttemptService quizAttemptService;
    private UserService  userService;
    private QuizService quizService;


    @PostMapping("/quizAttemp")
    public QuizAttempt submitQuizAttempt(@RequestParam Long userId, @RequestParam Long quizId, @RequestParam int score) {
        User user = userService.getById(userId);
        Quiz quiz = quizService.getQuizById(quizId);
        return quizAttemptService.saveQuizAttempt(user, quiz, score);
    }
}
