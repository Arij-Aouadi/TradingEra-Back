package tn.esprit.shadowtradergo.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.shadowtradergo.DAO.Entities.FinancialTerm;

import java.util.List;

public interface FinancialTermRepository extends JpaRepository<FinancialTerm, Long> {
    List<FinancialTerm> findByThemeId(Long themeId);

}
