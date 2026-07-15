package com.tutorial.resourceserver.dto;
import com.tutorial.resourceserver.dto.userPackage.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDtoWidthUser {
    private UserRequest userRequest;
    private ClientDto clientDto;
}
