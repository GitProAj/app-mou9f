package com.tutorial.resourceserver.controller;

import com.tutorial.resourceserver.dto.MessageDto;
import com.tutorial.resourceserver.entity.ImageClient;
import com.tutorial.resourceserver.entity.VideoClient;
import com.tutorial.resourceserver.service.OperationImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resource")
public class UploadImageController {
    @Autowired
    private OperationImage operationImage;
    @PostMapping("/uploadImage")
    public ResponseEntity<MessageDto> uploadImage(
                                @RequestParam("titre") String titre,
                                @RequestParam("description") String description,
                                @RequestParam("fichier") MultipartFile fichier) {
        return operationImage.uploadFile(titre, description, fichier);   
    }
    @GetMapping("/test")
    public ResponseEntity<MessageDto> user(Authentication authentication){
        return ResponseEntity.ok(new MessageDto("bonjour  "+"authentication.getName()",""));
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageClient>> getImages() {
        return ResponseEntity.ok(operationImage.getAllImageByCurrentUser());
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER')")

    public ResponseEntity<MessageDto> deleteImage(@PathVariable long id) {
        return ResponseEntity.ok(operationImage.deleteImage(id));
    }

}
