package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.Question;
import tn.esprit.shadowtradergo.DAO.Entities.Quiz;
import tn.esprit.shadowtradergo.Services.Classes.QuestionService;
import tn.esprit.shadowtradergo.Services.Classes.QuizService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/quizzes")

public class QuizController {
    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }
    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByQuizId(@PathVariable Long quizId) {
        Quiz quiz = quizService.getQuizWithQuestions(quizId);

        if (quiz != null) {
            List<Question> questions = quiz.getQuestions();
            return ResponseEntity.ok(questions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody Quiz quiz) {
        // Enregistrez le nouveau quiz
        Quiz savedQuiz = quizService.saveQuiz(quiz);

        return ResponseEntity.ok("Quiz created with ID: " + savedQuiz.getId());
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<String> addQuestionToQuiz(
            @PathVariable Long quizId,
            @RequestBody Question question
    ) {
        // Assurez-vous que le quiz avec l'ID spécifié existe
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }

        // Associez la question au quiz
        question.setQuiz(quiz);

        // Enregistrez la question
        Question savedQuestion = questionService.saveQuestion(question);

        return ResponseEntity.ok("Question added with ID: " + savedQuestion.getId() + " to Quiz with ID: " + quiz.getId());
    }
}
