package com.mou9f.web;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }


//    @PostMapping("/logout")
    public void logoutSession(HttpSecurity http) throws Exception {
        http.logout(
                ses -> {
                    ses
                            .logoutSuccessUrl("http://127.0.0.1:8081/home/accueil")
                            .deleteCookies("JSESSIONID")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true);
                });

//        return "login?logout";
    }
}
