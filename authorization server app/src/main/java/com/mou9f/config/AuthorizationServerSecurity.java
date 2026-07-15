package com.mou9f.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity

public class AuthorizationServerSecurity {
   @Bean
    @Order(1)
    SecurityFilterChain securityFilterChain(HttpSecurity http)   throws Exception{
       OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(withDefaults()
                );
       http.exceptionHandling(ex->ex.defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
        )).oauth2ResourceServer(jwt->jwt.jwt(withDefaults()));
        http.cors(cors->cors.configurationSource(corsConfigurationSource()));
       return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain webSecurityFilterChain2(HttpSecurity http) throws Exception {
        http
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .csrf(c->c.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/error","/adduser","/getUsers","/updateStatus").permitAll()
                        .requestMatchers("/static/**", "/webjars/**", "/favicon.ico","/css/**").permitAll()
                        .anyRequest().authenticated()
                )
//              .csrf(c->c.ignoringRequestMatchers("/auth/**","/client/**","/adduser"))
//                .formLogin(form -> form
//                        .loginPage("/login")
//                )
                .formLogin(form -> form
                        .loginPage("/login"))
//                        .loginProcessingUrl("/authLogin")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .successHandler(new AuthenticationSuccessHandler() {
//                            @Override
//                            public void onAuthenticationSuccess(HttpServletRequest request,
//                                                                HttpServletResponse response,
//                                                                Authentication authentication)
//                                    throws IOException {
//
//                                response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8081");
//                                response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:4200");
//                                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//                                response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
//                                response.setHeader("Access-Control-Allow-Credentials", "true");
//                                response.setHeader("Access-Control-Max-Age", "3600");
//
//                                response.setStatus(HttpServletResponse.SC_OK);
//                                response.setContentType("application/json");
//                                response.getWriter().write("{\"status\":\"success\"}");
//                                response.sendRedirect("http://127.0.0.1:8081/home/client");
//                            }
//                        })
//                        .failureHandler(new AuthenticationFailureHandler() {
//                            @Override
//                            public void onAuthenticationFailure(HttpServletRequest request,
//                                                                HttpServletResponse response,
//                                                                AuthenticationException exception)
//                                    throws IOException {
//                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                                response.setContentType("application/json");
//                                response.getWriter().write("{\"status\":\"error\",\"message\":\"" +
//                                        exception.getMessage() + "\"}");
//                            }
//                        }).permitAll()
//
//                        .defaultSuccessUrl("http://127.0.0.1:8081/home/client", true).permitAll()
//                        )

//                        .failureUrl("http://127.0.0.1:8081/home/login?error=true")
//                        .permitAll()
                .logout(logout->
                    logout
                            .logoutUrl("/logout")
//                            .logoutSuccessUrl("http://127.0.0.1:8081/home/accueil")
//                            .deleteCookies("JSESSIONID")
//                            .invalidateHttpSession(true)
//                            .clearAuthentication(true)
                )
                .oauth2ResourceServer(rs->rs.jwt(withDefaults()));
                return   http.build();
    }

//   @Bean
//    public CorsConfigurationSource corsConfigurationSource(){
//
//        CorsConfiguration configuration=new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:4200","http://127.0.0.1:8081","http://localhost:8081"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","PUT","DELETE"));
//        configuration.addAllowedHeader("*");
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:8081",
                "http://127.0.0.1:8081",
                "http://localhost:4200",
                "http://127.0.0.1:4200"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }
    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }


    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().issuer("http://localhost:9000").build();
    }


}
