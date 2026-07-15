package com.tutorial.resourceserver.controller;

import com.tutorial.resourceserver.dto.MessageDto;
import com.tutorial.resourceserver.entity.VideoClient;
import com.tutorial.resourceserver.service.OperationVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/videos")
public class UploadVideoController {
        @Autowired
        private OperationVideo videoService;

        @PostMapping("/upload")
        public ResponseEntity<MessageDto> uploadVideo(
                        @RequestParam("titre") String titre,
                        @RequestParam("description") String description,
                        @RequestParam("fichier") MultipartFile fichierVideo) {
            return  videoService.uploadVideo(titre, description, fichierVideo);

        }

    @GetMapping("/videos")
    public ResponseEntity<List<VideoClient>> getVideos() {
        return ResponseEntity.ok(videoService.getAllVideoByCurrentUser());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER')")

    public ResponseEntity<MessageDto> deleteVideo(@PathVariable Long id) {
            return ResponseEntity.ok(videoService.deleteVideo(id));
    }

    @GetMapping("/user")
    public ResponseEntity<MessageDto> user(Authentication authentication){
        return ResponseEntity.ok(new MessageDto("",authentication.getName()));
    }

    @PostMapping("/test")
    public ResponseEntity<MessageDto> uploadVideo(@RequestParam("titre") String titre, @RequestParam("description") String description) {

            System.out.println(titre);
            System.out.println(description);
        return ResponseEntity.ok(new MessageDto("","le video est enregistrer"));

    }
}
