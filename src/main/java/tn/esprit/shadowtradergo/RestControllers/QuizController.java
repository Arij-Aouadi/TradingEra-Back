package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.Question;
import tn.esprit.shadowtradergo.DAO.Entities.Quiz;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.DAO.Entities.UserAnswer;
import tn.esprit.shadowtradergo.Services.Classes.QuestionService;
import tn.esprit.shadowtradergo.Services.Classes.QuizService;
import tn.esprit.shadowtradergo.Services.Classes.UserAnswerService;
import tn.esprit.shadowtradergo.Services.Classes.UserService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/quizzes")

public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService utilisateurService;

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }
    @GetMapping("/{quizId}/questions/{questionId}")
    public ResponseEntity<Question> getQuestionByQuizIdAndQuestionId(
            @PathVariable Long quizId,
            @PathVariable Long questionId
    ) {
        Quiz quiz = quizService.getQuizWithQuestions(quizId);

        if (quiz != null) {
            // Trouver la question avec l'ID spécifié
            Optional<Question> optionalQuestion = quiz.getQuestions().stream()
                    .filter(question -> question.getId().equals(questionId))
                    .findFirst();

            // Si la question est trouvée, la retourner
            if (optionalQuestion.isPresent()) {
                return ResponseEntity.ok(optionalQuestion.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
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


    @GetMapping("/{quizId}/time")
    public ResponseEntity<Integer> getRemainingTime(@PathVariable Long quizId) {
        // Récupérez le quiz par ID
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }

        // Calculez le temps restant (en secondes) et renvoyez-le
        int remainingTime = calculateRemainingTime(quiz);

        // Log pour déboguer
        System.out.println("Remaining Time: " + remainingTime);

        return ResponseEntity.ok(remainingTime);
    }

    private int calculateRemainingTime(Quiz quiz) {
        if (quiz.getStartTime() == null) {
            // Le temps de début n'est pas défini, renvoyez le temps limite complet
            return quiz.getTimeLimit();
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = quiz.getStartTime();

        // Si l'heure de début est après l'heure d'arrêt initiale, utilisez l'heure de début
        LocalDateTime endTime = startTime.isAfter(currentTime)
                ? startTime.plusMinutes(5)
                : currentTime.plusMinutes(5);

        // Utilisez ChronoUnit.SECONDS.between pour obtenir la différence en secondes
        long remainingSeconds = ChronoUnit.SECONDS.between(currentTime, endTime);

        // Ajoutez des journaux pour déboguer
        System.out.println("Current Time: " + currentTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Remaining Seconds: " + remainingSeconds);

        // Assurez-vous que remainingSeconds est positif
        int remainingTime = (int) Math.max(remainingSeconds, 0);

        // Ajoutez un autre journal pour déboguer
        System.out.println("Remaining Time: " + remainingTime);

        return remainingTime;
    }






    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody Quiz quiz) {
        // Définir le temps de début sur le moment actuel
        quiz.setStartTime(LocalDateTime.now());

        // Définir le temps limite par défaut (en secondes) si ce n'est pas spécifié
        if (quiz.getTimeLimit() <= 0) {
            quiz.setTimeLimit(Quiz.DEFAULT_TIME_LIMIT);
        }

        // Enregistrez le nouveau quiz
        Quiz savedQuiz = quizService.saveQuiz(quiz);

        return ResponseEntity.ok("Quiz created with ID: " + savedQuiz.getId());
    }

    @PostMapping("/{quizId}/questions/{questionId}/submit")
    public ResponseEntity<Integer> submitQuiz(
            @PathVariable Long quizId,
            @PathVariable Long questionId,
            @RequestBody String userResponse
    ) {
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }

        Question question = questionService.getQuestionById(questionId);

        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        // Créez et associez la réponse à l'utilisateur
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setQuiz(quiz);
        userAnswer.setQuestion(question);
        userAnswer.setUserResponse(userResponse);

        // Ajoutez la réponse de l'utilisateur à la liste userAnswers du quiz
        quiz.getUserAnswers().add(userAnswer);

        // Enregistrez la réponse
        userAnswerService.saveUserAnswer(userAnswer);

        // Calculer la note
        int score = calculateScore(quiz);
        return ResponseEntity.ok(score);
    }



    private int calculateScore(Quiz quiz) {
        int score = 0;

        for (UserAnswer userAnswer : quiz.getUserAnswers()) {
            Question question = userAnswer.getQuestion();

            // Ajoutez une vérification pour s'assurer que la question n'est pas null
            if (question != null) {
                Set<String> correctAnswers = question.getCorrectAnswers();
                String userResponse = userAnswer.getUserResponse();

                // Enlevez les guillemets entourant la userResponse
                userResponse = userResponse.replaceAll("\"", "");

                // Ajoutez des messages de journalisation pour déboguer
                System.out.println("Question: " + question.getContent());
                System.out.println("User Response: " + userResponse);
                System.out.println("Correct Answers: " + correctAnswers);

                // Comparer la réponse de l'utilisateur aux réponses correctes
                if (correctAnswers.contains(userResponse)) {
                    score++;
                }
            }
        }

        return score;
    }
    @GetMapping("/{quizId}/questions/{questionId}/score")
    public ResponseEntity<Integer> getQuizScore(
            @PathVariable Long quizId,
            @PathVariable Long questionId
    ) {
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }

        Question question = questionService.getQuestionById(questionId);

        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        // Calculer la note sans soumettre une réponse
        int score = calculateScore(quiz);
        return ResponseEntity.ok(score);
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

    /*@PostMapping("/evaluer/amateur")
    public User evaluerQuizAmateur(@RequestBody User utilisateur) {
        utilisateurService.attribuerBadge(utilisateur, "Amateur");
        return utilisateur;
    }

    @PostMapping("/evaluer/professionnel")
    public User evaluerQuizProfessionnel(@RequestBody User utilisateur) {
        utilisateurService.attribuerBadge(utilisateur, "Professionnel");
        return utilisateur;
    }*/
    @GetMapping("/afficher-utilisateur-avec-badge")
    public String afficherUtilisateurAvecBadge(Model model) {
        // Get connected user from the service
        User connectedUser = utilisateurService.getConnectedUser();

        // Assign badge to the connected user
        utilisateurService.attribuerBadge(connectedUser, "Amateur"); // You can change the quiz type as needed

        // Add the user and badge information to the model
        model.addAttribute("connectedUser", connectedUser);

        // Return the view name (assuming you have a corresponding Thymeleaf or JSP template)
        return "user-with-badge-view";
    }



    @GetMapping("/utilisateurs/actifs")
    public List<User> getUtilisateursActifs() {
        List<User> utilisateurs = getUtilisateurs();

        // Récupérez les trois premiers utilisateurs
        int limit = Math.min(3, utilisateurs.size());
        return utilisateurs.subList(0, limit);
    }
    private List<User> getUtilisateurs() {
        // Utilisez le service pour récupérer la liste des utilisateurs
        return utilisateurService.getAllUtilisateurs();
    }
}
