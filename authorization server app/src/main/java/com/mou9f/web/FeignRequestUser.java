package com.mou9f.web;

import com.mou9f.dto.UserDto;
import com.mou9f.dto.UserResponse;
import com.mou9f.dto.UserResponseDto;
import com.mou9f.repository.UserRepository;
import com.mou9f.service.ServiceFeignUser;
import com.mou9f.service.UserOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class FeignRequestUser {
//    @Autowired
//    UserRepository userRepository;
    @Autowired
    ServiceFeignUser serviceFeignUser;
//    @Autowired
//    UserOperation userOperation;
//
//    @PostMapping("/adduser")
//    public ResponseEntity<UserResponse> addUser(@RequestBody UserDto userDto){
//        return  ResponseEntity.status(HttpStatus.CREATED)
//                .body(userOperation.createUser(userDto));
//    }
//
//    @DeleteMapping("/delete/{username}")
//    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String username){
//        try {
//            userRepository.deleteByUsername(username);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
    @GetMapping("/getUsers")
    public Set<UserResponseDto> getUser(){
            return  serviceFeignUser.getUsers();
    }
}
