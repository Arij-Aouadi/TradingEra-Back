package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.shadowtradergo.DAO.Entities.Role;
import tn.esprit.shadowtradergo.DAO.Entities.TypeRole;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByTypeRole(TypeRole typeRole);
}
