package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.*;
import tn.esprit.shadowtradergo.DAO.Repositories.ActionRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.OrdreRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.PositionRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.UserRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IPositionService;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class PositionService implements IPositionService {
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UserRepository userRepository;
    private EntityManager entityManager;

    @Autowired
    private OrdreRepository ordreRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private UserService userService; // Assuming you have a UserService

    @Override
    public List<Position> selectall() {
        return positionRepository.findAll();
    }

    @Override
    public Position add(Position position) {
        return positionRepository.save(position);
    }
    @Override
    public Position edit(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public List<Position> selectAll() {
        return (List<Position>)positionRepository.findAll();
    }

    @Override
    public Position selectById(Long idP) {
        return positionRepository.findById(idP).get();    }

    @Override
    public void deleteById(Long idP) {positionRepository.deleteById(idP);

    }



    @Override
    public void delete(Position position) {

        positionRepository.delete(position);

    }
    @Override
    public Position getById(long idP) {
        return positionRepository.findById(idP).get();
    }

    @Override
    public void deleteAll(List<Position> list) {
        positionRepository.deleteAll(list);
    }

    public List<Position> getCurrentUserPositions() {
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            List<Position> filteredPositions = currentUser.getPositions().stream()
                    .filter(position -> !"archivé".equalsIgnoreCase(position.getStatusPosition()))
                    .collect(Collectors.toList());

            return new ArrayList<>(filteredPositions);
        }

        return Collections.emptyList();
    }

    public void miseAjourSolde(Ordre ordre) {
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            Double solde = currentUser.getSolde();
            System.out.println("Solde actuel : " + solde);

            // Assurez-vous que le solde n'est pas nul, sinon initialiser à 0.0
            if (solde == null) {
                currentUser.setSolde(0.0);
                solde = 0.0;
                System.out.println("Solde initialisé à 0.0");
            }

            double montant = 0.0;

            if (ordre.getTypetransaction() == TypeTransaction.Sell) {
                montant = ordre.getPrixOrdre() * ordre.getQuantite();
            } else if (ordre.getTypetransaction() == TypeTransaction.Buy) {
                montant = -ordre.getPrixOrdre() * ordre.getQuantite();
            }

            // Mettre à jour le solde
            currentUser.setSolde(solde + montant);
            userService.edit(currentUser);

            System.out.println("Solde mis à jour : " + currentUser.getSolde());
        } else {
            System.out.println("Utilisateur non connecté. Solde non mis à jour.");
        }
    }

    public void creerOrdreVenteEtMettreAJourStatut(Position position) {
        Ordre ordreVente = new Ordre();
        ordreVente.setTypetransaction(TypeTransaction.Sell);
        position.setStatusPosition("archivé");
        positionRepository.save(position);
        //ordreService.passerOrdre(ordreVente);
    }
    public double calculerRevenu() {
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            List<Position> positions = getCurrentUserPositions();

            double totalRevenu = 0.0;

            for (Position userPosition : positions) {
                if (!"archivé".equalsIgnoreCase(userPosition.getStatusPosition())) {
                    double revenu = (userPosition.getValeurActuelle() - userPosition.getPrixAchat()) * userPosition.getQuantité();
                    totalRevenu += revenu;
                }
            }

            currentUser.setRevenu(totalRevenu);
            userService.edit(currentUser);

            return totalRevenu;
        }

        return 0.0;
    }
    public int countCurrentUserPositions() {
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            long count = currentUser.getPositions().stream()
                    .filter(position -> !"archivé".equalsIgnoreCase(position.getStatusPosition()))
                    .count();

            return (int) count;
        }

        return 0;
    }

    @Override
    public double calculerProfit(Position position) {
        return (position.getValeurActuelle() - position.getPrixAchat()) * position.getQuantité();}

    public List<Map<String, Object>> calculerPourcentageRevenuParPosition() {
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            List<Position> positions = getCurrentUserPositions();
            double totalRevenu = calculerRevenu();

            List<Map<String, Object>> result = new ArrayList<>();

            for (Position userPosition : positions) {
                if (!"archivé".equalsIgnoreCase(userPosition.getStatusPosition())) {
                    double revenu = (userPosition.getValeurActuelle() - userPosition.getPrixAchat()) * userPosition.getQuantité();
                    double pourcentageRevenu = (revenu / totalRevenu) * 100;

                    Map<String, Object> positionData = new HashMap<>();
                    positionData.put("label", userPosition.getNom());  // Nom de la position
                    positionData.put("value", pourcentageRevenu);      // Pourcentage du revenu total

                    result.add(positionData);
                }
            }

            return result;
        }

        return Collections.emptyList();
    }

}






