package com.chatop.api.controller;

import com.chatop.api.dto.UserDTO;
import com.chatop.api.model.UserLogin;
import com.chatop.api.model.LoginResponse;
import com.chatop.api.model.UserRegister;
import com.chatop.api.security.JwtIssuer;
import com.chatop.api.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.chatop.api.service.UserService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // To avoid using constructor to initialize method
public class UserController {
    private final JwtIssuer jwtIssuer;

	@Autowired
	private UserService userService;

    private final AuthenticationManager authenticationManager;

	@GetMapping("/me")
    public UserDTO getUserById(@AuthenticationPrincipal UserPrincipal principal) {
        return userService.getUserById(principal.getUserId());
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserLogin userLogin) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();
        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), principal.getName(), principal.getCreatedAt(), principal.getUpdatedAt());
        return LoginResponse.builder()
                .token(token)
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody UserRegister userRegister) {
        if (userService.isEmailAvailable(userRegister.getEmail())) {
            UserDTO userCreated = userService.createUser(userRegister);
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRegister.getEmail(), userRegister.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var principal = (UserPrincipal) authentication.getPrincipal();
            System.out.println(principal);

            var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), principal.getName(), principal.getCreatedAt(), principal.getUpdatedAt());
            LoginResponse jwt = LoginResponse.builder()
                    .token(token)
                    .build();
            return ResponseEntity.ok(jwt);
        }
        System.out.println("deja pris");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
