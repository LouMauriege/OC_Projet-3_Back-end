package com.chatop.api.service;

import com.chatop.api.dto.UserDTO;
import com.chatop.api.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	public UserDTO getUserById(Long id) throws Exception {
		Optional<User> user = userRepository.findById(id);
//		return user.map(userMapper::toDTO).orElse(null);
		if(user.isPresent()) {
			return userMapper.toDTO(user.get());
		} else {
			throw new Exception("No user correspond to this id");
		}
	}

}
