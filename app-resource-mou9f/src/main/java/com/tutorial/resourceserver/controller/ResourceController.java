package com.tutorial.resourceserver.controller;

import com.tutorial.resourceserver.dto.*;
import com.tutorial.resourceserver.dto.userPackage.UserRequest;
import com.tutorial.resourceserver.entity.ClientMou9f;
import com.tutorial.resourceserver.entity.ImageClient;
import com.tutorial.resourceserver.entity.VideoClient;
import com.tutorial.resourceserver.feign.AuthorizationFeign;
import com.tutorial.resourceserver.repository.ClientMou9fRepository;
import com.tutorial.resourceserver.service.*;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/open")
public class ResourceController {
    @Autowired
    private AuthorizationFeign authorizationFeign;
    @Autowired
    private SershCategory sershCategory;
    @Autowired
    private OperationClient operationClient;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OperationImage operationImage;
    @Autowired
    private OperationVideo operationVideo;
    @Autowired
    private ClientMou9fRepository clientMou9fRepository;
//    @GetMapping("/user")
//    public ResponseEntity<MessageDto> user(Authentication authentication){
//        return ResponseEntity.ok(new MessageDto("Hello " + authentication.getName()));
//    }
    @GetMapping("/user")
    public Map<String,String> user(Authentication authentication){
        return Map.of("Hello " , authentication.getName());
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Map<String,String> admin(Authentication authentication){
        return Map.of("Hello " , authentication.getName());
    }
//
//    @GetMapping("/admin")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<MessageDto> admin(Authentication authentication){
//        return ResponseEntity.ok(new MessageDto("Hello Mr. " + authentication.getName()));
//    }
    @GetMapping("/searchClients")
    public ResponseEntity<List<ClientMou9f>> searchClient(@PathVariable String activite){
      return ResponseEntity.status(HttpStatus.CREATED).body(sershCategory.searchClient(activite));
    }
    @PostMapping("/addClient")
    public ResponseEntity<MessageDto> addClient(@RequestBody ClientDto clientDto){
         MessageDto messageDto = null;
        try {
             messageDto = clientService.createClientWithUser(
                    clientDto, new UserRequest(clientDto.getUsername(),clientDto.getPassword(),clientDto.getRoles())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(messageDto);
        } catch (TransactionException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(messageDto);
        }
    }
    @GetMapping("getAllClient")
    public ResponseEntity<List<ClientResponseDto>> getAllClient(){
        return ResponseEntity.ok( operationClient.getAllClient());
    }
    @GetMapping("/activites")
    public ResponseEntity<List<String>> getAllActivites() {
        return ResponseEntity.ok(clientMou9fRepository.findAllDistinctActivites());
    }
    @GetMapping("/villes")
    public ResponseEntity<List<String>> getVillesByActivite(@RequestParam String activite) {
        return ResponseEntity.ok(clientMou9fRepository.findDistinctVillesByActivite(activite));
    }
    // 3. Récupérer les lieux par activité et ville
//    @GetMapping("/lieux")
//    public List<String> getLieuxByActiviteAndVille(
//            @RequestParam String activite,
//            @RequestParam String ville) {
//        return operationClient.clientMou9fFilter(activite, ville);
//    }
    // 4. Récupérer les clients filtrés
    @GetMapping("/clients")
    public Set<ClientResponseDto> getClientsByFilters(
            @RequestParam String activite,
            @RequestParam String ville) {
        return operationClient.clientMou9fFilter(activite, ville);
    }
    @GetMapping("/video/{id}")
    public ResponseEntity<List<VideoClient>> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok( operationVideo.getVideoById(id));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<List<ImageClient>> getImageById(@PathVariable Long id) {
        return ResponseEntity.ok(operationImage.getImageById(id));
    }










}
