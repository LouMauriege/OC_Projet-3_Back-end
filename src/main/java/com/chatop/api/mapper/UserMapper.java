package com.chatop.api.mapper;

import com.chatop.api.dto.UserDTO;
import com.chatop.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    // Convert to DTO
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // Convert to User
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        return user;
    }
}
