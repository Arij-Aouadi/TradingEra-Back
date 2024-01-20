package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.shadowtradergo.DAO.Entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
