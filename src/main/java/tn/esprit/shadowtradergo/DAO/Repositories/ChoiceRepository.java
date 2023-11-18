package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.shadowtradergo.DAO.Entities.Action;
import tn.esprit.shadowtradergo.DAO.Entities.Choice;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
