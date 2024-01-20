package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.shadowtradergo.DAO.Entities.*;

import java.util.List;

public interface OrdreRepository extends JpaRepository<Ordre, Long> {
    List<Ordre> findByAchat_User_IdOrVente_User_Id(Long userId1, Long userId2);
}
