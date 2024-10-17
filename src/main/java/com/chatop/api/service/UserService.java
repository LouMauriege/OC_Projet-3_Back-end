package com.chatop.api.service;

import com.chatop.api.dto.UserDTO;
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

	public UserDTO getUserById(Long id) throws Exception {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return userMapper.toDTO(user.get());
		} else {
			throw new Exception("No user correspond to this id");
		}
	}

	public Optional<UserDTO> findByName(String name) {
		Optional<User> user = userRepository.findByName(name);
		if (user.isEmpty()) {
			return Optional.empty();
		} else {
			User userFound = user.get();
			return Optional.of(userMapper.toDTO(userFound));
		}
	}

	public Optional<UserDTO> findByMail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty()) {
			return Optional.empty();
		} else {
			User userFound = user.get();
			return Optional.of(userMapper.toDTO(userFound));
		}
	}

	public boolean isEmailAvailable(String email) {
		Optional<UserDTO> userFind = findByMail(email);
        return userFind.isEmpty();
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
