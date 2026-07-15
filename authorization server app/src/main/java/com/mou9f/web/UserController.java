package com.mou9f.web;

import com.mou9f.dto.*;
import com.mou9f.entity.Client;
import com.mou9f.entity.User;
import com.mou9f.repository.UserRepository;
import com.mou9f.service.OperationClient;
import com.mou9f.service.UserOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    UserOperation userOperation;
    @Autowired
    OperationClient operationClient;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/adduser")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserDto userDto){
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(userOperation.createUser(userDto));
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String username){
        try {
                userRepository.deleteByUsername(username);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
    @PutMapping("/updateStatus")
    public Boolean updateStatus(@RequestBody UpdateStatusUser updateStatusUser){
        try {
            User user =  userRepository.findByUsername(updateStatusUser.getUsername()).get();
            user.setEnabled(updateStatusUser.isStatus());
            userRepository.save(user);
            return  user.isEnabled();
        } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDto> addClient(@RequestBody ClientDto clientDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(operationClient.createClient(clientDto));
    }



}
