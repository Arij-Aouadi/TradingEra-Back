package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.Question;
import tn.esprit.shadowtradergo.DAO.Entities.Quiz;
import tn.esprit.shadowtradergo.Services.Classes.QuestionService;
import tn.esprit.shadowtradergo.Services.Classes.QuizService;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    private QuizService quizService ;

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        Question question = questionService.getQuestionById(questionId);

        if (question != null) {
            return ResponseEntity.ok(question);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/quiz/{quizId}")
    public ResponseEntity<Question> createQuestion(@PathVariable Long quizId, @RequestBody Question question) {
        // Assurez-vous que le quiz avec l'ID spécifié existe
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz != null) {
            question.setQuiz(quiz); // Définissez le quiz pour la question
            Question createdQuestion = questionService.createQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId, @RequestBody Question updatedQuestion) {
        Question savedQuestion = questionService.updateQuestion(questionId, updatedQuestion);

        if (savedQuestion != null) {
            return ResponseEntity.ok(savedQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        boolean deleted = questionService.deleteQuestion(questionId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
