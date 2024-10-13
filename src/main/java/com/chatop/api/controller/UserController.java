package com.chatop.api.controller;

import com.chatop.api.dto.UserDTO;
import com.chatop.api.model.UserLogin;
import com.chatop.api.model.LoginResponse;
import com.chatop.api.security.JwtIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatop.api.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // To avoid using constructor to initialize method
public class UserController {
    private final JwtIssuer jwtIssuer;

	@Autowired
	private UserService userService;
	
	@GetMapping("user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws Exception {
        UserDTO userDto = userService.getUserById(id);
        if (userDto != null ) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserLogin userLogin) {
        var token = jwtIssuer.issue(1L, userLogin.getName());
        return LoginResponse.builder()
                .JWT(token)
                .build();
    }

    @GetMapping("secured")
    public String secured() {
        return "secured content";
    }

}
