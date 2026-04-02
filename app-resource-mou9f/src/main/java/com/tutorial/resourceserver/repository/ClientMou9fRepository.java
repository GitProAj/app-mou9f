package com.tutorial.resourceserver.repository;

import com.tutorial.resourceserver.entity.ClientMou9f;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientMou9fRepository extends JpaRepository<ClientMou9f,Long> {
//    List<ClientMou9f> findByTitreContainingIgnoreCase(String titre);
}
