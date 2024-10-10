package com.chatop.api.dto;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String name;
    private String email;
    private String password;
}
