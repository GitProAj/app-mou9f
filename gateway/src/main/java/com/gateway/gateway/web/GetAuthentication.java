package com.gateway.gateway.web;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/oauth2/resource")
public class GetAuthentication {
    @GetMapping("/auth")
    public Map<String,Object> logoutSession(Authentication authentication) throws Exception {

        return Map.of("auth",authentication);
    }

    @GetMapping("/getToken")
    public String debugToken(Authentication authentication) {
//        // L'annotation @AuthenticationPrincipal injecte directement le Principal
//        if (user != null) {
//            // Pour OIDC
//            user.getAuthorities();
//            return "Token: " + user.getIdToken().getTokenValue();
//        }
//        return "Non authentifié";
//    }
            if (authentication instanceof AuthenticatedPrincipal) {
//            OidcUser auth = (OidcUser) authentication;
                if (authentication.getPrincipal() instanceof DefaultOidcUser) {
                    return ((DefaultOidcUser) authentication.getPrincipal()).getIdToken().getTokenValue();
               }
            }
            return "";
    }
}
