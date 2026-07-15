package com.tutorial.resourceserver.service;

import com.tutorial.resourceserver.entity.ClientMou9f;
import com.tutorial.resourceserver.event.TransactionFailedEvent;
import com.tutorial.resourceserver.feign.AuthorizationFeign;
import com.tutorial.resourceserver.repository.ClientMou9fRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TransactionCompensation {
    private  static  final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientMou9fRepository clientRepository;

    @Autowired
    private AuthorizationFeign authClient;

    @EventListener
    @Async
    public void handleTransactionFailure(TransactionFailedEvent event) {
        ClientMou9f client = event.getClient();
        // Compensation: Supprimer le client
        if (client.getUsername() != null) {
            try {
                // Essayer de supprimer l'utilisateur de l'Auth Server aussi
                authClient.deleteUser(client.getUsername());
            } catch (Exception e) {
                logger.error("Erreur lors de la suppression de l'utilisateur: {}", e.getMessage());
            }
        }
        clientRepository.delete(client);
    }
}
