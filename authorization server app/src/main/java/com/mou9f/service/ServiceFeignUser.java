package com.mou9f.service;

import com.mou9f.dto.UserResponseDto;
import com.mou9f.entity.User;
import com.mou9f.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceFeignUser {
   @Autowired
    UserRepository userRepository;

    public Set<UserResponseDto> getUsers(){
         return userRepository.findAll().stream().map(
                 this::mapUser
         ).collect(Collectors.toSet());
    }
    private UserResponseDto mapUser(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEnabled(user.isEnabled());
        userResponseDto.setSubscriptionEndDate(user.getSubscriptionEndDate());
        userResponseDto.setTrialPeriod(user.isTrialPeriod());
        userResponseDto.setSubscriptionStartDate(user.getSubscriptionStartDate());
        return userResponseDto;
    }


}
