package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Game;
import tn.esprit.shadowtradergo.DAO.Entities.Historique;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.DAO.Repositories.GameRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.HistoriqueRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.OrdreRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.UserRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IGameService;
import tn.esprit.shadowtradergo.Services.Interfaces.IHistoriqueService;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class HistoriqueService implements IHistoriqueService {
    @Autowired
    HistoriqueRepository historiqueRepository;
     @Autowired
    UserRepository userRepository;
     @Autowired
     OrdreRepository orderRepository;
     @Autowired
    GameRepository gameRepository;
    @Autowired
    private UserService userService;

  /*  @Override
    public List<Historique> getHistoriqueWithOrderDetailsByUserIdAndDateRange(Long userId, Date startDate, Date endDate) {




            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));

            List<Historique> historiqueList;

            if (startDate != null && endDate != null) {
                historiqueList = historiqueRepository.findByUserAndOrdreDateOrdreBetweenOrderByOrdreDateOrdreDesc(user, startDate, endDate);
            } else {
                historiqueList = historiqueRepository.findByUserOrderByOrdreDateOrdreDesc(user);
            }

            for (Historique historique : historiqueList) {
                Ordre order = historique.getOrdre();
                if (order != null) {
                    // Populate relevant fields in Historique from Ordre
                    historique.setQuantite(order.getQuantite());
                    historique.setTypeordre(order.getTypeordre());
                    // Add more fields as needed
                }
            }

            return historiqueList;
        } */

   /* @Override
    public List<Historique> TableauHistorique(Long userId, Date startDate, Date endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));

        List<Historique> historiqueList;

        if (startDate != null && endDate != null) {
            historiqueList = historiqueRepository.findByUserAndOrdreDateOrdreBetweenOrderByOrdreDateOrdreDesc(user, startDate, endDate);
        } else {
            historiqueList = historiqueRepository.findByUserOrderByOrdreDateOrdreDesc(user);
        }

        for (Historique historique : historiqueList) {
            Ordre order = historique.getOrdre();
            if (order != null) {
                // Populate relevant fields in Historique from Ordre
                historique.setQuantite(order.getQuantite());
                historique.setTypeordre(order.getTypeordre());
                // Add more fields as needed
            }
        }

        return historiqueList;
    }*/




        public List<Historique> getTableauHistorique() {
            User currentUser = userService.getCurrentUser();
            List<Historique> historiqueList = historiqueRepository.findByOrderByOrdreDateOrdreDesc();

            for (Historique historique : historiqueList) {
                Ordre order = historique.getOrdre();
                if (order != null) {
                    historique.setDateOrdre(order.getDateOrdre());
                    historique.setTypeSymbol(order.getTypeSymbol());
                    historique.setSymbole(order.getSymbol());
                    historique.setTypeordre(order.getTypeordre());
                    historique.setQuantity(order.getQuantite());
                    historique.setPrixOrdre(order.getPrixOrdre());
                }
            }

            return historiqueList;
        }
    }








