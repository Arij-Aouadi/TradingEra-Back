package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.Choice;
import tn.esprit.shadowtradergo.DAO.Entities.Question;
import tn.esprit.shadowtradergo.Services.Classes.ChoiceService;
import tn.esprit.shadowtradergo.Services.Classes.QuestionService;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/choices")
public class ChoiceController {
    @Autowired
    private ChoiceService choiceService;
    private QuestionService questionService ;

    @GetMapping
    public List<Choice> getAllChoices() {
        return choiceService.getAllChoices();
    }

    @GetMapping("/{choiceId}")
    public ResponseEntity<Choice> getChoiceById(@PathVariable Long choiceId) {
        Choice choice = choiceService.getChoiceById(choiceId);

        if (choice != null) {
            return ResponseEntity.ok(choice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/question/{questionId}")
    public ResponseEntity<Choice> createChoice(@PathVariable Long questionId, @RequestBody Choice choice) {
        // Vérifiez si la question correspondante existe
        Question question = questionService.getQuestionById(questionId);

        if (question != null) {
            // Associez le choix à la question
            choice.setQuestion(question);

            // Créez le choix
            Choice createdChoice = choiceService.createChoice(choice);

            // Ajoutez le choix à la liste des choix de la question (si nécessaire)
            question.getChoices().add(createdChoice);

            // Mettez à jour la question (si nécessaire)
            questionService.updateQuestion(questionId, question);

            // Retournez le choix créé
            return ResponseEntity.status(HttpStatus.CREATED).body(createdChoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{choiceId}")
    public ResponseEntity<Choice> updateChoice(@PathVariable Long choiceId, @RequestBody Choice updatedChoice) {
        Choice choice = choiceService.updateChoice(choiceId, updatedChoice);

        if (choice != null) {
            return ResponseEntity.ok(choice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{choiceId}")
    public ResponseEntity<Void> deleteChoice(@PathVariable Long choiceId) {
        boolean deleted = choiceService.deleteChoice(choiceId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
