package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.shadowtradergo.DAO.Entities.Game;
import tn.esprit.shadowtradergo.DAO.Entities.Historique;
import tn.esprit.shadowtradergo.DAO.Entities.User;

import java.util.Date;
import java.util.List;

public interface HistoriqueRepository  extends JpaRepository<Historique, Long> {




    List<Historique> findByOrderByOrdreDateOrdreDesc();
}
