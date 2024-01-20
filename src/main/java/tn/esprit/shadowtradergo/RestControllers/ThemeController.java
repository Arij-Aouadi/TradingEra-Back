package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.FinancialTerm;
import tn.esprit.shadowtradergo.DAO.Entities.Theme;
import tn.esprit.shadowtradergo.Services.Classes.FinancialTermService;
import tn.esprit.shadowtradergo.Services.Classes.ThemeService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/themes")
public class ThemeController {
    @Autowired
    private ThemeService themeService;
    private FinancialTermService financialTermService ;

    @GetMapping
    public ResponseEntity<List<Theme>> getAllThemes() {
        List<Theme> themes = themeService.getAllThemes();
        return ResponseEntity.ok(themes);
    }
    @GetMapping("/{themeId}/financial-terms")
    public ResponseEntity<List<FinancialTerm>> getFinancialTermsByThemeId(@PathVariable Long themeId) {
        List<FinancialTerm> financialTerms = financialTermService.getFinancialTermsByThemeId(themeId);

        if (!financialTerms.isEmpty()) {
            return ResponseEntity.ok(financialTerms);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long themeId) {
        themeService.deleteTheme(themeId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<String> createTheme(@RequestBody Theme theme) {
        Theme savedTheme = themeService.createTheme(theme);
        return ResponseEntity.ok("Theme created with ID: " + savedTheme.getId());
    }
}
