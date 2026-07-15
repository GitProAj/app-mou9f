package com.tutorial.resourceserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientResponseDto {
    private long id;
    private String firstname;
    private String lastname;
    private String username;
    private String ville_activite;
    private String lieut_activite;
    private String phone;
    private String activite;
    private boolean enabled;
    private boolean trialPeriod;

}
