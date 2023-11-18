package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.Role;
import tn.esprit.shadowtradergo.DAO.Entities.TypeRole;

import java.util.List;
import java.util.Optional;

public interface OrdreRepository extends JpaRepository<Ordre, Long> {
    List<Ordre> findByAchat_User_IdOrVente_User_Id(Long userId1, Long userId2);
}
