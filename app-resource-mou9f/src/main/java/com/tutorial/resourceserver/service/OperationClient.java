package com.tutorial.resourceserver.service;

import com.tutorial.resourceserver.dto.*;
import com.tutorial.resourceserver.entity.ClientMou9f;
import com.tutorial.resourceserver.feign.AuthorizationFeign;
import com.tutorial.resourceserver.repository.ClientMou9fRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class OperationClient {
    @Autowired
    private ClientMou9fRepository clientMou9fRepository;
    @Autowired
    private AuthorizationFeign authorizationFeign;
    public MessageDto addClient(ClientMou9fDto clientDto){

            Optional<ClientMou9f> existingClient = clientMou9fRepository.findByUsername(clientDto.username());
            if (existingClient.isPresent()) {
                return new MessageDto("not added","");
            }else{
                // Si tout est OK, ajouter le client
                ClientMou9f client = new ClientMou9f();
                client.setFirstname(clientDto.firstname());
                client.setLastname(clientDto.lastname());
                client.setUsername(clientDto.username());
                client.setVille_activite(clientDto.ville_activite());
                client.setLieut_activite(clientDto.lieut_activite());
                client.setPhone(clientDto.phone());
                client.setActivite(clientDto.activite());
                ClientMou9f savedClient = clientMou9fRepository.save(client);
                return new MessageDto("client added","");
            }

       }
    public List<ClientResponseDto> getAllClient() {
        List<ClientResponseDto> clients = clientMou9fRepository.findAll()
                                            .stream()
                                            .map(this::mouveClient)
                                            .collect(Collectors.toList());
        clients.forEach(clientResponseDto->
                {
                    authorizationFeign.getUsers().stream()
                            .filter(userResponseDto ->
                                    userResponseDto.getUsername().equals(clientResponseDto.getUsername())
                            )
                            .findFirst()
                            .ifPresent(userResponseDto ->
                                    {
                                        clientResponseDto.setEnabled(userResponseDto.isEnabled());
                                        clientResponseDto.setTrialPeriod(userResponseDto.isTrialPeriod());
                                    }
                            );
                });

        return clients;
    }
     private ClientResponseDto mouveClient(ClientMou9f client){

            return new ClientResponseDto(
                 client.getId(),
                 client.getFirstname(),
                 client.getLastname(),
                 client.getUsername(),
                 client.getVille_activite(),
                 client.getLieut_activite(),
                 client.getPhone(),
                 client.getActivite(),
                 false,
                 false
            ) ;
     }
     public Set<ClientResponseDto> clientMou9fFilter(String activite, String ville) {
         return authorizationFeign.getUsers()
                 .stream()
                 .filter(userResponseDto -> {
//                     return (userResponseDto.isSubscriptionActive() &&
//                             userResponseDto.getSubscriptionEndDate() != null &&
//                             LocalDateTime.now().isBefore(userResponseDto.getSubscriptionEndDate()))
//                             ||
//                             (userResponseDto.isTrialPeriod() &&
//                                     userResponseDto.getTrialEndDate() != null &&
//                                     LocalDateTime.now().isBefore(userResponseDto.getTrialEndDate()));
                     return this.isSubscriptionValid(userResponseDto);
                 })
                 .flatMap(userResponseDto -> {
                     return clientMou9fRepository.findDistinctClientByActiviteAndVille(activite, ville)
                             .stream()
                             .filter(user -> user.getUsername().equals(userResponseDto.getUsername()))
                             .map(this::mouveClient);
                 })
                 .collect(Collectors.toSet());
     }

         public boolean isSubscriptionValid(UserResponseDto userResponseDto) {
             if (!userResponseDto.isEnabled())
                 return false;
             if ( userResponseDto.isTrialPeriod() && userResponseDto.getTrialEndDate() != null) {
                 return LocalDateTime.now().isBefore(userResponseDto.getTrialEndDate());
             }
             if (userResponseDto.getSubscriptionEndDate() != null) {
                 return LocalDateTime.now().isBefore(userResponseDto.getSubscriptionEndDate());
             }
             return true;
         }
    }



