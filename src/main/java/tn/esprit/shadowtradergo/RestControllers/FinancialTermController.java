package tn.esprit.shadowtradergo.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.shadowtradergo.DAO.Entities.FinancialTerm;
import tn.esprit.shadowtradergo.DAO.Entities.Theme;
import tn.esprit.shadowtradergo.DAO.Repositories.FinancialTermRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.ThemeRepository;
import tn.esprit.shadowtradergo.Services.Classes.FileStorageService;
import tn.esprit.shadowtradergo.Services.Classes.FinancialTermService;
import tn.esprit.shadowtradergo.Services.Classes.ThemeService;

import java.io.IOException;
import java.lang.System;


import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/financial-terms")
public class FinancialTermController {
    @Autowired
    private FinancialTermService financialTermService;
    @Autowired
    private FileStorageService fileStorageService ;
    @Autowired
    private ThemeRepository themeRepository ;
    @Autowired
    private FinancialTermRepository financialTermRepository ;

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
        try {
            Theme theme = themeRepository.findById(themeId)
                    .orElseThrow(() -> new RuntimeException("Theme not found with id: " + themeId));

            financialTerm.setTheme(theme);

            FinancialTerm savedFinancialTerm = financialTermRepository.save(financialTerm);

            // Ajouter des logs pour vérifier l'URL de la vidéo
            System.out.println("FinancialTerm ajouté avec succès. ID : " + savedFinancialTerm.getId());

            if (savedFinancialTerm.getVideoUrl() != null) {
                System.out.println("URL de la vidéo : " + savedFinancialTerm.getVideoUrl());
            } else {
                System.out.println("Aucune URL de vidéo spécifiée.");
            }

            return ResponseEntity.ok(savedFinancialTerm);
        } catch (Exception e) {
            // Ajouter des logs pour capturer toute exception
            System.err.println("Erreur lors de l'ajout du FinancialTerm : " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }




    @PutMapping("/{id}")
    public ResponseEntity<FinancialTerm> updateFinancialTerm(
            @PathVariable Long id,
            @RequestBody FinancialTerm financialTerm) {
        // Ajoutez l'imagePath ici (c'est-à-dire, définissez l'imagePath de financialTerm)
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
    @GetMapping("/theme/{themeId}")
    public ResponseEntity<List<FinancialTerm>> getAllFinancialTermsByTheme(@PathVariable Long themeId) {
        List<FinancialTerm> financialTerms = financialTermService.getAllFinancialTermsByTheme(themeId);
        return ResponseEntity.ok(financialTerms);
    }



}
