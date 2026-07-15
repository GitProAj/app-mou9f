package com.mou9f.config;

import com.mou9f.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class TokenConsomizerConfig {

    @Bean
    public  OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
        return context -> {
            Authentication principal = context.getPrincipal();
            if(context.getTokenType().getValue().equals("id_token")){
                context.getClaims().claim("token_type","id token");
                Set<String> roles = principal.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
                context.getClaims().claim("roles",roles).claim("username", principal.getName());

            }
            if(context.getTokenType().getValue().equals("access_token")){
                context.getClaims().claim("token_type","access token");
                Set<String> roles = principal.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
                context.getClaims().claim("roles",roles).claim("username", principal.getName());
            }
        };
    }

}
