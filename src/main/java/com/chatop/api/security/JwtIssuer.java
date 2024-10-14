package com.chatop.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtIssuer {
    private final JwtProperties jwtProperties;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public String issue(Long userId, String email, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("email", email)
                .withClaim("name", name)
                .withClaim("createdAt", createdAt.format(formatter))
                .withClaim("updatedAt", updatedAt.format(formatter))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
