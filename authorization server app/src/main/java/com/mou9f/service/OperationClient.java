package com.mou9f.service;

import com.mou9f.dto.ClientDto;
import com.mou9f.dto.MessageDto;
import com.mou9f.entity.Client;
import com.mou9f.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OperationClient implements RegisteredClientRepository {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public MessageDto createClient(ClientDto clientDto) {
        try {
            Client client = new Client();
            if (clientRepository.findByClientId(clientDto.getClientId()).isPresent())
            {
                return new MessageDto("le client est exist");
            }else{
                client.setClientId(clientDto.getClientId());
                client.setClientSecret(passwordEncoder.encode(clientDto.getClientSecret()));
                client.setAuthenticationMethods(clientDto.getAuthenticationMethods());
                client.setAuthorizationGrantTypes(clientDto.getAuthorizationGrantTypes());
                client.setScopes(clientDto.getScopes());
                client.setRedirectUris(clientDto.getRedirectUris());
                client.setPostLogoutRedirectUris(clientDto.getPostLogoutRedirectUris());
                client.setRequirePoofKey(clientDto.isRequirePoofKey());
                clientRepository.save(client);
                return new MessageDto("client created");
            }
        } catch (Exception e) {
            return new MessageDto("client not created");
        }
    }
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
