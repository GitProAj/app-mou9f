package com.tutorial.resourceserver.repository;

import com.tutorial.resourceserver.entity.ClientMou9f;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientMou9fRepository extends JpaRepository<ClientMou9f,Long> {
//  List<ClientMou9f> findByTitreContainingIgnoreCase(String titre);
//    List<ClientMou9f> findByVille_activiteAndLieut_activiteAndActivite(String ville, String lieut , String activite);
//    List<ClientMou9f> findByVille_activiteAndActivite(String ville , String activite);
    Optional<ClientMou9f> findByUsername(String username);
    List<ClientMou9f> findByActivite( String activite );

    @Query("SELECT DISTINCT c.activite FROM ClientMou9f c " +
            "WHERE c.activite IS NOT NULL")
    List<String> findAllDistinctActivites();

    @Query("SELECT DISTINCT c.ville_activite FROM ClientMou9f c " +
            "WHERE c.activite=:activite " +
            "AND c.ville_activite IS NOT NULL")
    List<String> findDistinctVillesByActivite(@Param("activite") String activite);

//    @Query("SELECT DISTINCT c.lieut_activite FROM ClientMou9f c " +
//            "WHERE c.activite=:activite " +
//            "AND c.ville_activite=:ville " +
//            "AND c.lieut_activite IS NOT NULL")
//    List<String> findDistinctLieutByActivite(@Param("activite") String activite, @Param("ville") String ville);
    @Query("SELECT DISTINCT c FROM ClientMou9f c " +
            "WHERE c.activite=:activite " +
            "AND c.ville_activite=:ville " +
            "AND c.lieut_activite IS NOT NULL")
    List<ClientMou9f> findDistinctClientByActiviteAndVille(@Param("activite") String activite, @Param("ville") String ville);

//    @Query("SELECT DISTINCT c FROM ClientMou9f c " +
//            "WHERE c.activite=:activite " +
//            "AND c.ville_activite=:ville " +
//            "AND c.lieut_activite=:lieut  " +
//            "AND c IS NOT NULL")
//    List<ClientMou9f> findDistinctClientFilter(@Param("activite") String activite, @Param("ville") String ville, @Param("lieut") String lieut);

}
