package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.Position;

import java.util.List;

public interface PositionRepository  extends JpaRepository<Position, Long> {
    Position findByUser_Id(Long userId);
    List<Position> findByUserId(Long userId);

}
