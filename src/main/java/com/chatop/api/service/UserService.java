package com.chatop.api.service;

import com.chatop.api.dto.UserDTO;
import com.chatop.api.exception.UserNotFound;
import com.chatop.api.mapper.UserMapper;
import com.chatop.api.model.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;

import lombok.Data;

import java.util.Optional;

@Data
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRepository userRepository;

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public UserDTO getUserById(Long id) {
		return userMapper.toDTO(userRepository.findById(id).orElseThrow(
				() -> new UserNotFound("Utilisateur non trouvé !")));
	}

	public UserDTO findByMail(String email) {
		return userMapper.toDTO(userRepository.findByEmail(email).orElseThrow(
				() -> new UserNotFound("Utilisateur non trouvé !")));
	}

	public boolean isEmailAvailable(String email) {
        return findByMail(email) == null;
	}

	public UserDTO createUser(UserRegister userRegister) {
		User user = new User();
		user.setEmail(userRegister.getEmail());
		user.setName(userRegister.getName());
		user.setPassword(passwordEncoder().encode(userRegister.getPassword()));
		User createdUser = userRepository.save(user);
		return userMapper.toDTO(createdUser);
	}

}
