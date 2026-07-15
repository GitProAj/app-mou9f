package com.gateway.gateway.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/gatewayMou9f")
public class GetAuthentication {
    @GetMapping("/user")
    public ResponseEntity<String> logoutSession(Authentication authentication) throws Exception {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        return ResponseEntity.ok(authentication.getName());
    }

    @GetMapping("/token")
    private Map<String,String> Token(@AuthenticationPrincipal OidcUser user) {
        if (user != null) {
            // Pour OIDC
            String token =user.getIdToken().getTokenValue();
            return Map.of("token",token);
        }
        return null;
    }


}
