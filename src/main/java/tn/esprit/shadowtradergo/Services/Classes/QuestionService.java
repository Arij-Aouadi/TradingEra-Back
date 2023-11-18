package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Question;
import tn.esprit.shadowtradergo.DAO.Repositories.QuestionRepository;

import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizService quizService ;


    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    public Question saveQuestion(Question question) {
        // Ajoutez ici la logique de validation ou de traitement avant l'ajout
        return questionRepository.save(question);
    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    public Question createQuestion(Question question) {
        // Ajoutez ici la logique de validation ou de traitement avant l'ajout
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long questionId, Question updatedQuestion) {
        Question existingQuestion = questionRepository.findById(questionId).orElse(null);

        if (existingQuestion != null) {
            // Mettez à jour les propriétés de la question existante avec les valeurs de la question mise à jour
            existingQuestion.setContent(updatedQuestion.getContent());
            // ... mettez à jour d'autres propriétés au besoin

            // Enregistrez les modifications dans la base de données
            return questionRepository.save(existingQuestion);
        } else {
            return null; // Ou lancez une exception si nécessaire
        }
    }

    public boolean deleteQuestion(Long questionId) {
        if (questionRepository.existsById(questionId)) {
            questionRepository.deleteById(questionId);
            return true;
        }
        return false;
    }
}
