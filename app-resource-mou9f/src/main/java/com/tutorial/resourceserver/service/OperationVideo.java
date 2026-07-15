package com.tutorial.resourceserver.service;

import com.tutorial.resourceserver.dto.MessageDto;
import com.tutorial.resourceserver.entity.ImageClient;
import com.tutorial.resourceserver.entity.VideoClient;
import com.tutorial.resourceserver.repository.ClientMou9fRepository;
import com.tutorial.resourceserver.repository.VideoRepository;
import com.tutorial.resourceserver.service.athentification.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OperationVideo {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientMou9fRepository clientMou9fRepository;

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
//        video.setCheminVideo(cheminFichier.toString());
        video.setTypeVideo(fileVideo.getContentType());
        video.setTailleVideo(fileVideo.getSize());
        video.setDateUpload(LocalDateTime.now());

        return videoRepository.save(video);

    }

    // Option 2: Stocker la vidéo directement dans la BDD (pour petits fichiers)
    public ResponseEntity<MessageDto> uploadVideo(String titre, String description, MultipartFile fichierVideo) {
        String username = userService.getCurrentUsername();
        long idClient = clientMou9fRepository.findByUsername(username).get().getId();
        try {
            VideoClient video = new VideoClient();
            video.setIdClient(idClient);
            video.setTitre(titre);
            video.setDescription(description);
            video.setContenuVideo(fichierVideo.getBytes());
            video.setTypeVideo(fichierVideo.getContentType());
            video.setTailleVideo(fichierVideo.getSize());
            video.setDateUpload(LocalDateTime.now());
             videoRepository.save(video);
            return new ResponseEntity<>(new MessageDto("video added to data base",""), HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(new MessageDto("","error to added video"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Récupérer une vidéo par son ID
    public List<VideoClient> getVideoById(Long id) {
         return videoRepository.findByIdClient(id);
    }
    // Supprimer une vidéo

    public MessageDto deleteVideo(Long id) {
        Optional<VideoClient> videoOpt = videoRepository.findById(id);
        try {
            videoRepository.deleteById(id);
            return new MessageDto("video execute deleted", "");
        } catch (Exception e) {
            return new MessageDto("", "video not deleted");
        }
    }

    public List<VideoClient> getAllVideoByCurrentUser(){
        long clientId = clientMou9fRepository.findByUsername(
                userService.getCurrentUsername()
        ).get().getId();
        return videoRepository.findByIdClient(clientId);
    }
}
