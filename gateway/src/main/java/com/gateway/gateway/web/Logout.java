package com.gateway.gateway.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Logout {

    @GetMapping("/pageLogout")
    public String logout(){
        return "logout";
    }
}
