package com.mou9f.service;


import com.mou9f.entity.Client;
import com.mou9f.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

//@Service

public class ConectedClientRegestration implements RegisteredClientRepository {
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientRepository.findByClientId(id)
                .orElseThrow(()->new RuntimeException("client not fond"));
        return toRegisterClient(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(()->new RuntimeException("client not fond"));
        return toRegisterClient(client);
    }
    private RegisteredClient toRegisterClient(Client client){
        RegisteredClient.Builder registeredClient=RegisteredClient.withId(client.getClientId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(cm->cm.addAll(convertClientAuthenticationMethod(client)))
                .authorizationGrantTypes(ag->ag.addAll(convertAuthorizationGrantType(client)))
                .scopes(s->s.addAll(client.getScopes()))
                .redirectUris(r->r.addAll(client.getRedirectUris()))
                .postLogoutRedirectUris(p->p.addAll(client.getPostLogoutRedirectUris()))
                .clientIdIssuedAt(new Date().toInstant());
        return registeredClient.build();
    }

    public static Set<ClientAuthenticationMethod> convertClientAuthenticationMethod(Client client){
        return client.getAuthenticationMethods().stream()
                .map(String::trim)
                .map(ClientAuthenticationMethod::new)
                .collect(Collectors.toSet());
    }
    public static Set<AuthorizationGrantType> convertAuthorizationGrantType(Client client) {
        return client.getAuthorizationGrantTypes().stream()
                .map(String::trim)
                .map(AuthorizationGrantType::new)
                .collect(Collectors.toSet());
    }
}
