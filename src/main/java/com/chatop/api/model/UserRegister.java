package com.chatop.api.model;

import lombok.Data;

@Data
public class UserRegister {
    private String email;
    private String name;
    private String password;
}
