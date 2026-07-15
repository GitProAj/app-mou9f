package com.mou9f.dto;

import com.mou9f.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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


