package com.tutorial.resourceserver.repository;

import com.tutorial.resourceserver.entity.ImageClient;
import com.tutorial.resourceserver.entity.VideoClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageClient,Long> {
    List<ImageClient> findByIdClient(long idClient);
}
