package com.gateway.gateway.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class Login {

    @GetMapping("/auth/login")
    public Mono<Void> redirectToAngularLogin(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create("/home/login"));
        return exchange.getResponse().setComplete();
    }

    @GetMapping("/oauth2/redirect")
    public Mono<Void> handleOAuth2Redirect(ServerWebExchange exchange) {
        String code = exchange.getRequest().getQueryParams().getFirst("code");
        String state = exchange.getRequest().getQueryParams().getFirst("state");

        if (code != null) {
            String redirectUrl = "http://localhost:4200/oauth2/callback?code=" + code + "&state=" + state;
            exchange.getResponse().setStatusCode(HttpStatus.FOUND);
            exchange.getResponse().getHeaders().setLocation(URI.create(redirectUrl));
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.FOUND);
            exchange.getResponse().getHeaders().setLocation(URI.create("http://localhost:4200/login?error=true"));
        }
        return exchange.getResponse().setComplete();
    }
}
