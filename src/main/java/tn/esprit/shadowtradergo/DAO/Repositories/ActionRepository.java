package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.shadowtradergo.DAO.Entities.Action;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;

public interface ActionRepository extends JpaRepository<Action, Long> {
    @Query("SELECT a.coursActuel FROM Action a WHERE a.idA = :actionId")
    Double findLatestCoursDeMarche(@Param("actionId") Long actionId);
}
