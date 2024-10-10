package com.chatop.api.service;

import com.chatop.api.dto.RegisterUserDTO;
import com.chatop.api.dto.UserDTO;
import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDTO registerUserDTO) {
        UserDTO user = new UserDTO();
        user.setName(registerUserDTO.getName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        UserDTO userCreated = userRepository.save(user);
    }
}
