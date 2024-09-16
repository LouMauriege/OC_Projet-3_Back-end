package com.chatop.api.mapper;

import com.chatop.api.dto.UserDTO;
import com.chatop.api.model.User;

public class UserMapper {
    // Convert to DTO
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // Convert to User
    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setId(userDTO.getId());
        return user;
    }
}
