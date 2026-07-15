package com.tutorial.resourceserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @ManyToMany(fetch = FetchType.EAGER)
    private long idClient;
    private String title;
    private String description;
    // Pour stocker la vidéo directement dans la BDD (BLOB)
    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] bytesImage;
    private String contentTypeImage; // MIME type (video/mp4, etc.)
    private Long sizeImage;
    private LocalDateTime dateUpload;
}
