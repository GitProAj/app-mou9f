package com.tutorial.resourceserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    private int id;
    private String username;
    private boolean enabled ;
    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;
    private boolean trialPeriod;
    private LocalDateTime trialEndDate;

        }


