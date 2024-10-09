package com.chatop.api.inteface;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;

public class CustomSigningKeyResolver implements SigningKeyResolver {
    private final SecretKey secretKey;

    public CustomSigningKeyResolver(String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        return secretKey;
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, String claimsInString) {
        return secretKey;
    }
}
