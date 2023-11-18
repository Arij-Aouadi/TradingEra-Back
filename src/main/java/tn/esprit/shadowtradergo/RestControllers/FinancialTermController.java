package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.FinancialTerm;
import tn.esprit.shadowtradergo.DAO.Entities.Theme;
import tn.esprit.shadowtradergo.DAO.Repositories.FinancialTermRepository;
import tn.esprit.shadowtradergo.Services.Classes.FinancialTermService;
import tn.esprit.shadowtradergo.Services.Classes.ThemeService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/financial-terms")
public class FinancialTermController {
    @Autowired
    private FinancialTermService financialTermService;

    @GetMapping
    public ResponseEntity<List<FinancialTerm>> getAllFinancialTerms() {
        List<FinancialTerm> financialTerms = financialTermService.getAllFinancialTerms();
        return ResponseEntity.ok(financialTerms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialTerm> getFinancialTermById(@PathVariable Long id) {
        FinancialTerm financialTerm = financialTermService.getFinancialTermById(id);
        if (financialTerm != null) {
            return ResponseEntity.ok(financialTerm);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{themeId}")
    public ResponseEntity<FinancialTerm> addFinancialTerm(
            @PathVariable Long themeId,
            @RequestBody FinancialTerm financialTerm) {
        FinancialTerm savedFinancialTerm = financialTermService.addFinancialTerm(themeId, financialTerm);
        return ResponseEntity.ok(savedFinancialTerm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialTerm> updateFinancialTerm(
            @PathVariable Long id,
            @RequestBody FinancialTerm financialTerm) {
        FinancialTerm updatedFinancialTerm = financialTermService.updateFinancialTerm(id, financialTerm);
        if (updatedFinancialTerm != null) {
            return ResponseEntity.ok(updatedFinancialTerm);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinancialTerm(@PathVariable Long id) {
        financialTermService.deleteFinancialTerm(id);
        return ResponseEntity.noContent().build();
    }
}
