package com.tutorial.resourceserver.feign;

import com.tutorial.resourceserver.dto.UserResponseDto;
import com.tutorial.resourceserver.dto.userPackage.UpdateStatusUser;
import com.tutorial.resourceserver.dto.userPackage.UserRequest;
import com.tutorial.resourceserver.dto.userPackage.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(name = "app-mou9f",url = "http://localhost:9000")
public interface AuthorizationFeign {

    @PostMapping("/adduser")
    ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest user);

    @DeleteMapping("/delete/{username}")
    ResponseEntity<Void>  deleteUser(@PathVariable String username);

    @GetMapping("/getUsers")
    Set<UserResponseDto> getUsers();

    @PutMapping("/updateStatus")
    boolean updateStatus(@RequestBody UpdateStatusUser updateStatusUser);

}
