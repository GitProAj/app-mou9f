package com.tutorial.resourceserver.dto.userPackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateStatusUser {
    private String username;
    private boolean status;
}
