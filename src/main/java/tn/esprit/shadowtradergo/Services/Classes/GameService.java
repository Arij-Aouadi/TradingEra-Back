package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Game;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Repositories.GameRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.OrdreRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IGameService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class GameService implements IGameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    OrdreRepository orderRepository;


////////METHODE QUI AIDE A AVOIR L'HISTORIQUE DE CHAQUE JEUX
   /* @Override
    public List<Ordre> getHistoriqueAchatVente(Long idGame) {
        Optional<Game> optionalGame = gameRepository.findById(idGame);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            // Charger les ordres associés au jeu depuis la base de données
            List<Ordre> ordres = game.getOrdres();

            // Filtrer les ordres en fonction de la date d'aujourd'hui
            LocalDate today = LocalDate.now();
            List<Ordre> filteredOrdres = ordres.stream()
                    .filter(ordre -> ordre.getDateOrdre().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(today))
                    .collect(Collectors.toList());

            // Trier les ordres filtrés par date décroissante
            Collections.sort(filteredOrdres, Comparator.comparing(Ordre::getDateOrdre).reversed());

            return filteredOrdres;
        } else {
            // Gérer le cas où le jeu avec l'ID spécifié n'est pas trouvé.
            return Collections.emptyList();
        }
    }
*/


}






