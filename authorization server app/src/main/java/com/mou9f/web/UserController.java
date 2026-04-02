package com.mou9f.web;

import com.mou9f.dto.ClientDto;
import com.mou9f.dto.MessageDto;
import com.mou9f.dto.UserDto;
import com.mou9f.entity.Client;
import com.mou9f.entity.User;
import com.mou9f.service.OperationClient;
import com.mou9f.service.UserOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    UserOperation userOperation;
    @Autowired
    OperationClient operationClient;
    @PostMapping("/user")
    public ResponseEntity<MessageDto> addUser(@RequestBody UserDto userDto){
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(userOperation.createUser(userDto));
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

}
