package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.FinancialTerm;
import tn.esprit.shadowtradergo.DAO.Entities.Theme;
import tn.esprit.shadowtradergo.DAO.Repositories.FinancialTermRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.ThemeRepository;

import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class FinancialTermService {
    @Autowired
    private FinancialTermRepository financialTermRepository;

    @Autowired
    private ThemeRepository themeRepository;

    public List<FinancialTerm> getAllFinancialTerms() {
        return financialTermRepository.findAll();
    }

    public FinancialTerm getFinancialTermById(Long id) {
        return financialTermRepository.findById(id).orElse(null);
    }

    public FinancialTerm addFinancialTerm(Long themeId, FinancialTerm financialTerm) {
        Theme theme = themeRepository.findById(themeId).orElse(null);
        if (theme != null) {
            financialTerm.setTheme(theme);
            return financialTermRepository.save(financialTerm);
        } else {
            // Gérer le cas où le thème n'est pas trouvé
            // Vous pouvez choisir de lever une exception, renvoyer une erreur, etc.
            return null;
        }
    }

    public FinancialTerm updateFinancialTerm(Long id, FinancialTerm financialTerm) {
        FinancialTerm existingFinancialTerm = financialTermRepository.findById(id).orElse(null);
        if (existingFinancialTerm != null) {
            // Mettez à jour les champs nécessaires
            existingFinancialTerm.setTerm(financialTerm.getTerm());
            existingFinancialTerm.setDefinition(financialTerm.getDefinition());
            // Mettez à jour d'autres champs selon vos besoins

            return financialTermRepository.save(existingFinancialTerm);
        } else {
            return null;
        }
    }

    public void deleteFinancialTerm(Long id) {
        financialTermRepository.deleteById(id);
    }
    public List<FinancialTerm> getFinancialTermsByThemeId(Long themeId) {
        return financialTermRepository.findByThemeId(themeId);
    }
}
