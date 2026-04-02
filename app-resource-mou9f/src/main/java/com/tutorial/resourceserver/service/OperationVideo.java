package com.tutorial.resourceserver.service;

import com.tutorial.resourceserver.entity.VideoClient;
import com.tutorial.resourceserver.repository.ClientMou9fRepository;
import com.tutorial.resourceserver.repository.VideoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OperationVideo {
    @Autowired
    private VideoRepository videoRepository;

    // Option 1: Stocker le chemin du fichier (recommandé)
    public VideoClient saveVideo(String titre , String description , MultipartFile fileVideo) throws IOException {
        // Définir le répertoire de stockage
        String uploadDir = "uploads/videos/";
        Path uploadPath = Paths.get(uploadDir);
        // Créer le répertoire s'il n'existe pas
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        // Générer un nom de fichier unique
        String nomFichier = System.currentTimeMillis() + "_" + fileVideo.getOriginalFilename();
        Path cheminFichier = uploadPath.resolve(nomFichier);
        // Copier le fichier
        Files.copy(fileVideo.getInputStream(), cheminFichier, StandardCopyOption.REPLACE_EXISTING);
        // Créer et sauvegarder l'entité
        VideoClient video = new VideoClient();
        video.setTitre(titre);
        video.setDescription(description);
        video.setCheminVideo(cheminFichier.toString());
        video.setTypeVideo(fileVideo.getContentType());
        video.setTailleVideo(fileVideo.getSize());
        video.setDateUpload(LocalDateTime.now());

        return videoRepository.save(video);

    }

    // Option 2: Stocker la vidéo directement dans la BDD (pour petits fichiers)
    public VideoClient sauvegarderVideoDansBDD(String titre, String description, MultipartFile fichierVideo) throws IOException {
        VideoClient video = new VideoClient();
        video.setTitre(titre);
        video.setDescription(description);
        video.setContenuVideo(fichierVideo.getBytes());
        video.setTypeVideo(fichierVideo.getContentType());
        video.setTailleVideo(fichierVideo.getSize());
        video.setDateUpload(LocalDateTime.now());

        return videoRepository.save(video);
    }

    // Récupérer toutes les vidéos
    public List<VideoClient> getAllVideos() {
        return videoRepository.findAll();
    }

    // Récupérer une vidéo par son ID
    public Optional<VideoClient> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    // Supprimer une vidéo
    public void deleteVideo(Long id) throws IOException {
        Optional<VideoClient> videoOpt = videoRepository.findById(id);
        if (videoOpt.isPresent()) {
            VideoClient video = videoOpt.get();

            // Supprimer le fichier physique si la vidéo est stockée sur le disque
            if (video.getCheminVideo() != null) {
                Path cheminFichier = Paths.get(video.getCheminVideo());
                Files.deleteIfExists(cheminFichier);
            }

            videoRepository.deleteById(id);
        }
    }
}
