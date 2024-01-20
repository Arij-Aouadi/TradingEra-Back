package tn.esprit.shadowtradergo.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.Question;
import tn.esprit.shadowtradergo.DAO.Entities.UserAnswer;
import tn.esprit.shadowtradergo.Services.Classes.QuestionService;
import tn.esprit.shadowtradergo.Services.Classes.UserAnswerService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/useranswer")
public class UserAnswerController {
    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/question/{questionId}")
    public ResponseEntity<UserAnswer> createUserAnswerForQuestion(
            @PathVariable Long questionId,
            @RequestBody UserAnswer userAnswer
    ) {
        // Vérifiez si la question correspondante existe
        Question question = questionService.getQuestionById(questionId);

        if (question != null) {
            // Associez la réponse de l'utilisateur à la question
            userAnswer.setQuestion(question);

            // Créez la réponse de l'utilisateur
            UserAnswer createdUserAnswer = userAnswerService.createUserAnswer(userAnswer);

            // Retournez la réponse de l'utilisateur créée
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserAnswer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserAnswer> getUserAnswerById(@PathVariable Long id) {
        Optional<UserAnswer> optionalUserAnswer = userAnswerService.getUserAnswerById(id);

        if (optionalUserAnswer.isPresent()) {
            UserAnswer userAnswer = optionalUserAnswer.get();
            return ResponseEntity.ok(userAnswer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<UserAnswer>> getUserAnswersByQuizId(@PathVariable Long quizId) {
        List<UserAnswer> userAnswers = userAnswerService.getUserAnswersByQuizId(quizId);
        return ResponseEntity.ok(userAnswers);
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserAnswer> updateUserAnswer(@PathVariable Long id, @RequestBody UserAnswer userAnswer) {
        UserAnswer updatedUserAnswer = userAnswerService.updateUserAnswer(id, userAnswer);
        return ResponseEntity.ok(updatedUserAnswer);
    }
    @GetMapping("/question/{questionId}/useranswers")
    public ResponseEntity<List<UserAnswer>> getChoicesForQuestion(@PathVariable Long questionId) {
        Question question = questionService.getQuestionById(questionId);

        if (question != null) {
            List<UserAnswer> choices = question.getUserAnswers();
            return ResponseEntity.ok(choices);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAnswer(@PathVariable Long id) {
        userAnswerService.deleteUserAnswer(id);
        return ResponseEntity.noContent().build();
    }


}
