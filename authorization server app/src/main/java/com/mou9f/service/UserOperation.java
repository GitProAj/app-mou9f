package com.mou9f.service;

import com.mou9f.dto.MessageDto;
import com.mou9f.dto.UserDto;
import com.mou9f.entity.Role;
import com.mou9f.entity.User;
import com.mou9f.enums.RoleName;
import com.mou9f.repository.RoleRepository;
import com.mou9f.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserOperation {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    public MessageDto createUser(UserDto userDto){
        User user ;
        try {
            if(userRepository.findByUsername(userDto.getUsername()).isPresent())
            {
                return new MessageDto("user exist");
            }else{
                user = new User();
                user.setUsername(userDto.getUsername());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                Set<Role> roles= new HashSet<>();
                userDto.getRoles().forEach(
                        r-> {
                            Role role = roleRepository.findByRole(RoleName.valueOf(r))
                                    .orElseThrow(()->new RuntimeException("role not fond"));
                            roles.add(role);
                        }
                );
                user.setRoles(roles);
                userRepository.save(user);
                return new MessageDto("user created");
            }
        }catch (Exception e){
            return new MessageDto("user not created");
        }
    }

}
