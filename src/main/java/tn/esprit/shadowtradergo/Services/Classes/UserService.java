package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.DAO.Repositories.UserRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IUserService;

import java.util.List;


@AllArgsConstructor
@Service
@NoArgsConstructor

public class UserService implements IUserService {




    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> selectall() {
        return userRepository.findAll();
    }
    public List<User> getAllUtilisateurs() {
        return userRepository.findAll(); // This retrieves all users from the database
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }
    @Override
    public User edit(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> selectAll() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public User selectById(Long id) {
        return userRepository.findById(id).get();    }

    @Override
    public void deleteById(Long id) {userRepository.deleteById(id);

    }



    @Override
    public void delete(User user) {

        userRepository.delete(user);

    }

    @Override
    public void deleteAll(List<User> list) {
        userRepository.deleteAll(list);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user= userRepository.findByUsername(currentUserName).get();
            return user;}
        else {
            return null;
        }
    }

    /*@Override
    public User assignAccountToUser(long userId, int accountNum) {
        Account account= accountRepository.findAccountByAccountNum(accountNum);
        User user=userRepository.findUserById(userId);
        user.getAccountList().add(account);
        account.setUser(user);
        return userRepository.save(user);
    }*/

    @Override
    public User getById(long id) {
        return userRepository.findById(id).get();
    }



    //TO Be changed later
    @Override
    public User getConnectedUser(){
        return this.selectById(2L);
    }
    public void attribuerBadge(User utilisateur, String typeQuiz) {
        int score = utilisateur.getScore();
        utilisateur.setNombreQuizEffectues(utilisateur.getNombreQuizEffectues() + 1);

        if (typeQuiz.equals("Amateur")) {
            // Logique de badges pour le quiz Amateur
            if (score >= 8) {
                utilisateur.setBadge("Amateur Expert");
            } else if (score >= 5) {
                utilisateur.setBadge("Amateur Intermédiaire");
            } else {
                utilisateur.setBadge("Amateur Débutant");
            }
        } else if (typeQuiz.equals("Professionnel")) {
            // Logique de badges pour le quiz Professionnel
            if (score >= 8) {
                utilisateur.setBadge("Professionnel Expert");
            } else if (score >= 5) {
                utilisateur.setBadge("Professionnel Intermédiaire");
            } else {
                utilisateur.setBadge("Professionnel Débutant");
            }
        } else {
            // Gérer d'autres types de quiz si nécessaire
            utilisateur.setBadge("Autre Type de Quiz");
        }
    }


}
