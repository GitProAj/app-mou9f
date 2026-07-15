package com.tutorial.resourceserver.config;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableFeignClients
public class FeignConfigInterceptor {

        @Bean
        public RequestInterceptor requestInterceptor() {
            return requestTemplate -> {
                // Récupérer le token du contexte de sécurité
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.getCredentials() instanceof String) {
                    requestTemplate.header("Authorization", "Bearer " + authentication.getCredentials());
                }
            };
        }
}

