package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Action;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.Position;
import tn.esprit.shadowtradergo.DAO.Repositories.ActionRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.OrdreRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.PositionRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IPositionService;

import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class PositionService implements IPositionService {
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private OrdreRepository ordreRepository;
    @Autowired
    private ActionRepository actionRepository;
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

    public double calculerRevenus(Long userId) {
        List<Ordre> ordres = ordreRepository.findByAchat_User_IdOrVente_User_Id(userId, userId);
        double revenus = 0.0;

        for (Ordre ordre : ordres) {
            double prixDeMarche = actionRepository.findLatestCoursDeMarche(ordre.getAction().getIdA());
            double valeurOrdre = ordre.getQuantite() * prixDeMarche;

            if ("ACHAT".equals(ordre.getTypeordre())) {
                revenus -= valeurOrdre;
            } else if ("VENTE".equals(ordre.getTypeordre())) {
                revenus += valeurOrdre;
            }
        }

        return revenus;
    }


    public double calculerValeurDuPortefeuille(Long userId) {
        // Charger la position de l'utilisateur en fonction de son ID
        Position position = positionRepository.findByUser_Id(userId);

        if (position != null) {
            Action action = position.getAchat().get(0).getAction(); // Obtenir l'entité Action associée à la position via l'ordre d'achat (assumant qu'il y a un seul ordre d'achat par position)

            if (action != null) {
                double prixDeMarché = action.getCoursActuel(); // Obtenir le prix de marché de l'action à partir de l'entité Action
                double valeurPosition = position.getQuantité() * prixDeMarché;
                return valeurPosition;
            }
        }

        // Retourner 0.0 si l'utilisateur n'a pas de position ou s'il y a d'autres erreurs
        return 0.0;
    }

}






