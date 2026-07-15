package com.tutorial.resourceserver.repository;

import com.tutorial.resourceserver.entity.ClientMou9f;
import com.tutorial.resourceserver.entity.VideoClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<VideoClient,Long> {
    List<VideoClient> findByTitreContainingIgnoreCase(String titre);
    List<VideoClient> findByIdClient(long idClient);
}
