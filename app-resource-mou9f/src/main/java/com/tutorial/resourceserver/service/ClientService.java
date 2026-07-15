package com.tutorial.resourceserver.service;

import com.tutorial.resourceserver.config.FeignConfigInterceptor;
import com.tutorial.resourceserver.dto.ClientDto;
import com.tutorial.resourceserver.dto.ClientMou9fDto;
import com.tutorial.resourceserver.dto.MessageDto;
import com.tutorial.resourceserver.dto.userPackage.UserRequest;
import com.tutorial.resourceserver.dto.userPackage.UserResponse;
import com.tutorial.resourceserver.entity.ClientMou9f;
import com.tutorial.resourceserver.event.TransactionFailedEvent;
import com.tutorial.resourceserver.feign.AuthorizationFeign;
import com.tutorial.resourceserver.repository.ClientMou9fRepository;
import jakarta.transaction.Transactional;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class ClientService {
        @Autowired
        private ApplicationEventPublisher applicationEventPublisher;
        @Autowired
        private ClientMou9fRepository clientRepository;

        @Autowired
        private AuthorizationFeign authClient;
        private  static  final Logger logger = LoggerFactory.getLogger(ClientService.class);

        // Pattern Saga avec compensation
        public MessageDto createClientWithUser(ClientDto clientDto,
                                                UserRequest userRequest) {
            // Étape 1: Sauvegarder le client localement (en attente)
            Optional<ClientMou9f> existingClient = clientRepository.findByUsername(userRequest.getUsername());
            ClientMou9f savedClient;
            if(existingClient.isPresent()){
                return new MessageDto("","le client est exist deja");
            }else {
                ClientMou9f client = new ClientMou9f();
                client.setFirstname(clientDto.getFirstname());
                client.setLastname(clientDto.getLastname());
                client.setVille_activite(clientDto.getVille_activite());
                client.setLieut_activite(clientDto.getLieut_activite());
                client.setPhone(clientDto.getPhone());
                client.setActivite(clientDto.getActivite());
                client.setStatus("PENDING");
                savedClient = clientRepository.save(client);
            }
            try {
                // Étape 2: Appel à Authorization Server
                ResponseEntity<UserResponse> response = authClient.saveUser(userRequest);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody().isSuccess()) {
                    // Succès: Mettre à jour le statut
                    savedClient.setStatus("ACTIVE");
                    savedClient.setUsername(userRequest.getUsername());
                    clientRepository.save(savedClient);
                    return new MessageDto("la creation du client est successful","");
                }
                else {
                    logger.error("Erreur lors de la création de l'utilisateur");
                    clientRepository.delete(savedClient);
                    // Échec: Annuler la transaction locale
                    return new MessageDto("","Échec de création de l'utilisateur");
//                   throw new RuntimeException("Échec de création de l'utilisateur");
                }
            } catch (TransactionException e) {
 //                Compensation: Supprimer le client local
//                supprimer le client avec applicationEventPublisher
//                TransactionFailedEvent trComt = new TransactionFailedEvent(this,savedClient,"Echoc de creation un client");
//                applicationEventPublisher.publishEvent(trComt);
                logger.error("Erreur lors de la création de l'utilisateur: {}", e.getMessage());
                clientRepository.delete(savedClient);
                return new MessageDto("","Erreur lors de la création de l'utilisateur: {} "+ e.getMessage());
            }
        }
        // Méthode avec retry
        @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
        public MessageDto createClientWithRetry(ClientDto clientRequest, UserRequest userRequest) {
            return createClientWithUser(clientRequest, userRequest);
        }
    }

