package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.shadowtradergo.DAO.Entities.Action;
import tn.esprit.shadowtradergo.DAO.Entities.Game;

public interface GameRepository  extends JpaRepository<Game, Long> {

}
