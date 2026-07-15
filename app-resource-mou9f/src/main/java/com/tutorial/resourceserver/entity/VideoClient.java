package com.tutorial.resourceserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @ManyToMany(fetch = FetchType.EAGER)
    private long idClient;
    private String titre;
    private String description;
    // Pour stocker le chemin du fichier vidéo
//    private String cheminVideo;
    // Pour stocker la vidéo directement dans la BDD (BLOB)
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] contenuVideo;
    private String typeVideo; // MIME type (video/mp4, etc.)
    private Long tailleVideo;
    private LocalDateTime dateUpload;

}
