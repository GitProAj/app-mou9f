package com.gateway.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.logout.*;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
//import java.net.URI;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired
    private ReactiveClientRegistrationRepository clientRegistrationRepository;
    @Value("${app.angular-url:http://localhost:4200}")
    private String angularUrl;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){
        return httpSecurity
//                .cors(c->c.configurationSource(corsConfigurationSource()))
                .csrf(c->c.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/home/**","oauth2/**").permitAll()
                        .anyExchange().authenticated()
                )
//                .oauth2Login(
//                        log->log
//                                .authorizationRequestResolver(resolver))
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
                .logout(withDefaults())
//                .logout(
//                            (logout) -> logout
//                                    .logoutUrl("/logout")
////                                    .logoutSuccessHandler(logoutSuccessHandler())
//                )
                .build();
}

//    private ServerLogoutSuccessHandler logoutSuccessHandler() {
//        return (exchange, authentication) -> {
//            ServerHttpResponse response = exchange.getExchange().getResponse();
//            response.setStatusCode(HttpStatus.OK);
//            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//            // Créer une réponse JSON
//            Map<String, String> body = new HashMap<>();
//            body.put("status", "success");
//            body.put("message", "Déconnexion réussie");
//            // Récupérer l'URL de logout de l'Auth Server si nécessaire
//            if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
//                ClientRegistration registration = clientRegistrationRepository
//                        .findByRegistrationId("gateway")
//                        .block();
//
//                if (registration != null) {
//                    String endSessionEndpoint = (String) registration.getProviderDetails()
//                            .getConfigurationMetadata()
//                            .get("end_session_endpoint");
//
//                    if (endSessionEndpoint != null) {
//                        String logoutUrl = UriComponentsBuilder.fromHttpUrl(endSessionEndpoint)
//                                .queryParam("post_logout_redirect_uri", angularUrl)
//                                .queryParam("client_id", registration.getClientId())
//                                .build()
//                                .toUriString();
//
//                        body.put("logoutUrl", logoutUrl);
//                    }
//                }
//            }
//            try {
//                ObjectMapper objectMapper = new ObjectMapper();
//                byte[] data = objectMapper.writeValueAsBytes(body);
//                return response.writeWith(Mono.just(response.bufferFactory().wrap(data)));
//            } catch (JsonProcessingException e) {
//                return Mono.error(e);
//            }
//        };
//    }
//    @Bean
//    ServerLogoutSuccessHandler oidcLogoutSuccessHandler(ReactiveClientRegistrationRepository repository) {
//        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(repository);
////        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("http://127.0.0.1:8081/logged-out");
//        // ou
//        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/logout-success");
//        return oidcLogoutSuccessHandler;
//    }

//    @Bean
//    ServerOAuth2AuthorizationRequestResolver pkceResolver(ReactiveClientRegistrationRepository repo) {
//        var resolver = new DefaultServerOAuth2AuthorizationRequestResolver(repo);
//        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());
//        return resolver;
//    }
}
