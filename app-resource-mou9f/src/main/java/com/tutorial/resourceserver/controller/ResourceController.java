package com.tutorial.resourceserver.controller;

import com.tutorial.resourceserver.dto.MessageDto;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//@RestController
//@RequestMapping("/api/videos")
public class ResourceController {

//    @GetMapping("/user")
//    public ResponseEntity<MessageDto> user(Authentication authentication){
//        return ResponseEntity.ok(new MessageDto("Hello " + authentication.getName()));
//    }
    @GetMapping("/user")
    public Map<String,String> user(Authentication authentication){
        return Map.of("Hello " , authentication.getName());
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Map<String,String> admin(Authentication authentication){
        return Map.of("Hello " , authentication.getName());
    }
//
//    @GetMapping("/admin")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<MessageDto> admin(Authentication authentication){
//        return ResponseEntity.ok(new MessageDto("Hello Mr. " + authentication.getName()));
//    }
}
