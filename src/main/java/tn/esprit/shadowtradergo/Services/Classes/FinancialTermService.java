package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.shadowtradergo.DAO.Entities.FinancialTerm;
import tn.esprit.shadowtradergo.DAO.Entities.Theme;
import tn.esprit.shadowtradergo.DAO.Repositories.FinancialTermRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.ThemeRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class FinancialTermService {
    @Autowired
    private FinancialTermRepository financialTermRepository;

    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private  FileStorageService fileStorageService ;

    public List<FinancialTerm> getAllFinancialTerms() {
        return financialTermRepository.findAll();
    }

    public FinancialTerm getFinancialTermById(Long id) {
        return financialTermRepository.findById(id).orElse(null);
    }

    public FinancialTerm addFinancialTerm(Long themeId, FinancialTerm financialTerm, MultipartFile videoFile) {
        try {
            Theme theme = themeRepository.findById(themeId)
                    .orElseThrow(() -> new RuntimeException("Theme not found with id: " + themeId));

            financialTerm.setTheme(theme);

            // Save the video file and update FinancialTerm
            if (videoFile != null && !videoFile.isEmpty()) {
                String videoUrl = fileStorageService.storeVideo(videoFile);
                financialTerm.setVideoUrl(videoUrl);

                // Ajouter des logs pour vérifier l'URL de la vidéo
                System.out.println("URL de la vidéo sauvegardée : " + videoUrl);
            } else {
                System.out.println("Aucune vidéo spécifiée.");
            }

            FinancialTerm savedFinancialTerm = financialTermRepository.save(financialTerm);

            // Ajouter des logs pour vérifier l'ID du FinancialTerm sauvegardé
            System.out.println("FinancialTerm sauvegardé avec succès. ID : " + savedFinancialTerm.getId());

            return savedFinancialTerm;
        } catch (Exception e) {
            // Ajouter des logs pour capturer toute exception
            System.err.println("Erreur lors de l'ajout du FinancialTerm : " + e.getMessage());
            throw new RuntimeException("Erreur lors de l'ajout du FinancialTerm", e);
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
    public List<FinancialTerm> getAllFinancialTermsByTheme(Long themeId) {
        // Implémentez la logique pour récupérer les termes financiers en fonction de l'ID du thème
        // Utilisez votre repository ou toute autre méthode appropriée pour récupérer les données
        // Retournez la liste de termes financiers associés au thème
        // Exemple avec le repository :
        Theme theme = themeRepository.findById(themeId).orElse(null);
        if (theme != null) {
            return theme.getFinancialTerms();
        } else {
            return Collections.emptyList();
        }
    }




}
