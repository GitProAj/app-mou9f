package com.gateway.gateway.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class ControllerLogout {

    @Autowired
    private ServerOAuth2AuthorizedClientRepository authorizedClientRepository;

    @Autowired
    private ReactiveClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/logout")
    public Mono<ResponseEntity<LogoutResponse>> initiateLogout(@AuthenticationPrincipal OidcUser user, ServerWebExchange exchange, Authentication authentication) {
        return exchange.getSession()
                .flatMap(session -> {
                    return session.invalidate()
                            .then(authorizedClientRepository.removeAuthorizedClient("gateway", authentication, exchange))
                            .then(Mono.fromRunnable(() -> {
                                SecurityContextHolder.clearContext();
                            }))
//                            .then(removeAllTokens(authentication))
                            .then(removeSessionCookies(exchange))
                            .then(Mono.defer(() -> {
                                return clientRegistrationRepository.findByRegistrationId("gateway")
                                        .map(registration -> {
                                            String logoutUrl = registration.getProviderDetails()
                                                    .getConfigurationMetadata()
                                                    .get("end_session_endpoint")
                                                    .toString();

//                                            String redirectUri = exchange.getRequest().getURI().resolve("/login").toString();
                                            String redirectUri = "http://127.0.0.1:8081/home/accueil";

                                            String idToken = extractIdToken(user);

                                            String fullLogoutUrl = UriComponentsBuilder.fromHttpUrl(logoutUrl)
                                                    .queryParam("post_logout_redirect_uri", redirectUri)
                                                    .queryParam("client_id", registration.getClientId())
                                                    .queryParam("id_token_hint", idToken)
                                                    .build()
                                                    .toUriString();

                                            return ResponseEntity.ok()
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .body(new LogoutResponse(fullLogoutUrl, "Déconnexion réussie"));
                                        });
                            }));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new LogoutResponse("/login", "Déjà déconnecté"))));
    }

//    private Mono<Void> removeAllTokens(Authentication authentication) {
//        return Mono.fromRunnable(() -> {
//            if (authentication != null && authentication instanceof OAuth2AuthenticationToken) {
//                String token = ((OAuth2AuthenticationToken) authentication).getPrincipal().getName();
//                // Supprimer du cache Redis
//                redisTemplate.delete("token:" + token);
//                redisTemplate.delete("user:" + authentication.getName());
//            }
//        });
//    }

    private Mono<Void> removeSessionCookies(ServerWebExchange exchange) {
        return Mono.fromRunnable(() -> {
            exchange.getResponse().getCookies().clear();
            // Supprimer spécifiquement les cookies de session
            exchange.getResponse().addCookie(ResponseCookie.from("SESSION")
                    .path("/")
                    .maxAge(0)
                    .build());

            exchange.getResponse().addCookie(ResponseCookie.from("JSESSIONID")
                    .path("/")
                    .maxAge(0)
                    .build());
        });
    }

    private String extractIdToken(@AuthenticationPrincipal OidcUser user) {
//        if (authentication instanceof AuthenticatedPrincipal) {
//            OidcUser auth = (OidcUser) authentication;
//            if (auth.getPrincipal() instanceof DefaultOidcUser) {
//                return ((DefaultOidcUser) auth.getPrincipal()).getIdToken().getTokenValue();
//           }
//            if (auth != null) {
//                return auth.getIdToken().getTokenValue();
//            }
//        }
//        return "";
//    }
            // L'annotation @AuthenticationPrincipal injecte directement le Principal
            if (user != null) {
                // Pour OIDC
                user.getAuthorities();
                return user.getIdToken().getTokenValue();
            }
            return "Non authentifié";
        }
    record LogoutResponse(String logoutUrl, String message) {
    }
}