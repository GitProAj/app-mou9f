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

    @GetMapping("/user9000")
    public Map<String,MessageDto> test(Authentication authentication){
        return  Map.of("9000 server authorization",new MessageDto(authentication.getName()));
    }
//    @GetMapping("/logout")
    public void logoutSession(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.logout(
                ses->{
                    ses
//                          .logoutSuccessUrl("/logout")
                            .deleteCookies("JSESSIONID")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true);
                }
        );
//        return ResponseEntity.ok("louged out");
    }

    @GetMapping("/auth")
    public Map<String,Object> logoutSession(Authentication authentication) throws Exception {

        return Map.of("auth",authentication);
    }

}
