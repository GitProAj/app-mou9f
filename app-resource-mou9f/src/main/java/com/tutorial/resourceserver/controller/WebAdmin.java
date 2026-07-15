package com.tutorial.resourceserver.controller;

import com.tutorial.resourceserver.dto.userPackage.UpdateStatusUser;
import com.tutorial.resourceserver.feign.AuthorizationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open")
public class WebAdmin {
    @Autowired
    AuthorizationFeign authorizationFeign;
    @PutMapping("/updateStatus")
    public ResponseEntity<Boolean> updateStatus(@RequestBody UpdateStatusUser updateStatusUser){
         return ResponseEntity.ok(authorizationFeign.updateStatus(updateStatusUser));
    }
}
