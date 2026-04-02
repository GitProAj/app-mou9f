package com.tutorial.resourceserver.controller;

import com.tutorial.resourceserver.dto.MessageDto;
import com.tutorial.resourceserver.entity.VideoClient;
import com.tutorial.resourceserver.service.OperationVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/videos")

//@RequestMapping("/resource")
public class VideoController {



        @Autowired
        private OperationVideo videoService;

        @PostMapping("/upload")
        public ResponseEntity<VideoClient> uploadVideo(
                @RequestParam("titre") String titre,
                @RequestParam("description") String description,
                @RequestParam("fichier") MultipartFile fichierVideo) {

            try {
                VideoClient video = videoService.sauvegarderVideoDansBDD(titre, description, fichierVideo);
                return new ResponseEntity<>(video, HttpStatus.CREATED);
            } catch (IOException e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        @PostMapping("/upload/bdd")
        public ResponseEntity<VideoClient> uploadVideoInDatabase(
                @RequestParam String titre,
                @RequestParam String description,
                @RequestParam MultipartFile fichierVideo) {

            try {
                VideoClient video = videoService.sauvegarderVideoDansBDD(titre, description, fichierVideo);
                return new ResponseEntity<>(video, HttpStatus.CREATED);
            } catch (IOException e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping
        public ResponseEntity<List<VideoClient>> getAllVideos() {
            List<VideoClient> videos = videoService.getAllVideos();
            return new ResponseEntity<>(videos, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<VideoClient> getVideoById(@PathVariable Long id) {
            return videoService.getVideoById(id)
                    .map(video -> new ResponseEntity<>(video, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<HttpStatus> deleteVideo(@PathVariable Long id) {
            try {
                videoService.deleteVideo(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
//    @GetMapping("/user")
//    public ResponseEntity<MessageDto> user(Authentication authentication){
//        return ResponseEntity.ok(new MessageDto("bonjour  "+authentication.getName()));
//    }
    @GetMapping("/user")
    public ResponseEntity<MessageDto> user(Authentication authentication){
        return ResponseEntity.ok(new MessageDto(authentication.getName()));
    }

    @PostMapping("/test")
    public ResponseEntity<MessageDto> uploadVideo(
            @RequestParam("titre") String titre,
            @RequestParam("description") String description) {

            System.out.println(titre);
            System.out.println(description);
        return ResponseEntity.ok(new MessageDto("le video est enregistrer"));

    }
}
