package tn.esprit.shadowtradergo.RestControllers;


import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/files")

public class FileController {
    private static final String UPLOAD_DIRECTORY = "uploads/";
    private static final String IMAGE_SUBDIRECTORY = "images/";
    private static final String VIDEO_SUBDIRECTORY = "videos/";

    @PostMapping("/upload")  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Veuillez sélectionner un fichier à télécharger.");
        }

        try {
            // Construire le chemin complet du fichier
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(uploadPath)) {
                // Créer le répertoire s'il n'existe pas
                Files.createDirectories(uploadPath);
            }

            // Construire l'URL de base en fonction du chemin du fichier
            String baseUrl = "http://localhost:1010"; // Remplacez par l'URL de votre serveur
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String relativePath = UPLOAD_DIRECTORY + file.getOriginalFilename();
            String fileUrl = baseUrl + "/" + relativePath;

            // Enregistrer le fichier sur le serveur
            Path filePath = uploadPath.resolve(file.getOriginalFilename()).toAbsolutePath().normalize();
            file.transferTo(filePath.toFile());

            // Retourner l'URL du fichier dans la réponse
            System.out.println("Chemin du fichier sauvegardé : " + filePath);
            System.out.println("URL du fichier : " + fileUrl);

            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            System.err.println("Erreur lors du téléchargement du fichier : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur lors du téléchargement du fichier : " + e.getMessage());
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toLowerCase();
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
            String[] filesArray = uploadPath.toFile().list();
            List<String> filesList = Arrays.asList(filesArray);
            return ResponseEntity.ok(filesList);
        } catch (Exception e) {
            e.printStackTrace(); // Ajoutez cette ligne pour imprimer la trace de la pile dans la console
            return ResponseEntity.status(500).body(Arrays.asList("Erreur lors de la récupération de la liste des fichiers : " + e.getMessage()));
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
            Path filePath = uploadPath.resolve(fileName).toAbsolutePath().normalize();

            // Supprimer le fichier
            Files.delete(filePath);

            return ResponseEntity.ok("Le fichier a été supprimé avec succès.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur lors de la suppression du fichier : " + e.getMessage());
        }
    }
}
