package com.tutorial.resourceserver.dto.userPackage;

import lombok.Data;
@Data
public class UserResponse {

        private String username;
        private boolean success;
        private String message;
}

