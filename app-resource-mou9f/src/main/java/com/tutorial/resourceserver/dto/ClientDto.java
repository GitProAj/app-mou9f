package com.tutorial.resourceserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String ville_activite;
    private String lieut_activite;
    private String phone;
    private String activite;
    private String[] roles;
}
