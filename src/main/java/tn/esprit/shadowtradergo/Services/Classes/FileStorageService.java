package tn.esprit.shadowtradergo.Services.Classes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service

public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    private static final String UPLOAD_DIRECTORY = "uploads/";

    public String storeFile(MultipartFile file, String subdirectory) {
        if (file.isEmpty()) {
            throw new RuntimeException("Veuillez sélectionner un fichier à télécharger.");
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY, subdirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName).toAbsolutePath().normalize();

            file.transferTo(filePath.toFile());

            String baseUrl = "http://localhost:1010"; // Replace with your server URL
            String relativePath = Paths.get(subdirectory, fileName).toString();
            return baseUrl + "/" + relativePath;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du téléchargement du fichier : " + e.getMessage());
        }
    }

    public String storeVideo(MultipartFile videoFile) {
        return storeFile(videoFile, "videos");
    }

    public String storeImage(MultipartFile imageFile) {
        return storeFile(imageFile, "images");
    }


    public String storeFile(MultipartFile file) throws IOException {
        // Obtenez le chemin complet du dossier d'upload
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();

        // Assurez-vous que le dossier d'upload existe, sinon créez-le
        Files.createDirectories(uploadPath);

        // Obtenez le chemin complet du fichier à stocker
        Path filePath = uploadPath.resolve(file.getOriginalFilename()).normalize();

        // Copiez le fichier vers le dossier d'upload
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retournez le chemin du fichier stocké
        return filePath.toString();
    }
}
