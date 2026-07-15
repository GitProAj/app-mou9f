package com.mou9f.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
//    @GetMapping("/login")
//    public String login(HttpServletRequest request) {
//        // Récupérer l'URL de votre frontend Angular (attention aux paramètres)
//        String redirectUrl = "http://127.0.0.1:8081/home/login";
//        // Garder le paramètre 'redirect_uri' ou 'state' pour le retour
//        // mais dans le flux OAuth2, l'AS gère ses propres paramètres.
//        return "redirect:" + redirectUrl;
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}





