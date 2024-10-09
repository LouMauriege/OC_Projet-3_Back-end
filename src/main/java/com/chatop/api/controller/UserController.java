package com.chatop.api.controller;

import com.chatop.api.dto.UserDTO;
import com.chatop.api.dto.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatop.api.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws Exception {
        UserDTO userDto = userService.getUserById(id);
        if (userDto != null ) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public String getToken(@ModelAttribute UserCredentials userCredentials) {
        String outPut = "d";
        return "Ok !";
    }
	
}
