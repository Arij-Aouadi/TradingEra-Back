package tn.esprit.shadowtradergo.Services.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.UserAnswer;
import tn.esprit.shadowtradergo.DAO.Repositories.UserAnswerRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserAnswerService {

    @Autowired
    private UserAnswerRepository userAnswerRepository ;

    public UserAnswer createUserAnswer(UserAnswer userAnswer) {
        // Ajoutez ici la logique nécessaire avant de sauvegarder l'entité UserAnswer
        return userAnswerRepository.save(userAnswer);
    }
    public Optional<UserAnswer> getUserAnswerById(Long id) {
        return userAnswerRepository.findById(id);
    }
    public void deleteUserAnswer(Long id) {
        userAnswerRepository.deleteById(id);
    }

    public UserAnswer updateUserAnswer(Long id, UserAnswer updatedUserAnswer) {
        // Vérifiez si l'utilisateur existe
        if (userAnswerRepository.existsById(id)) {
            // Assurez-vous que l'ID de l'objet à mettre à jour est défini correctement
            updatedUserAnswer.setId(id);

            // Ajoutez ici la logique nécessaire avant de sauvegarder l'entité mise à jour
            return userAnswerRepository.save(updatedUserAnswer);
        } else {
            // Gérez le cas où l'utilisateur n'existe pas
            return null; // Ou lancez une exception appropriée
        }
    }
    public List<UserAnswer> getUserAnswersByQuizId(Long quizId) {
        return userAnswerRepository.findByQuizId(quizId);
    }
    public UserAnswer saveUserAnswer(UserAnswer userAnswer) {
        return userAnswerRepository.save(userAnswer);
    }
}
