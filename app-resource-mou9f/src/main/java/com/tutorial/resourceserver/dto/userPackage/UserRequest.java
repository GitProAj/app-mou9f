package com.tutorial.resourceserver.dto.userPackage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    // DTOs
        private String username;
        private String password;
        private String[] roles;
    }



