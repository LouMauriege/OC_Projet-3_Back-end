package com.chatop.api.service;

import com.chatop.api.inteface.CustomSigningKeyResolver;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {

    // @Value("${security.jwt.key}")
    private String secretKey = "Y6kL91mpwzD/+cVCvvNYlo8qF6Z1dc+IhcyLyTVnWNFUOFMnOpNGzUSWMAsZ1N5x";

    CustomSigningKeyResolver keyResolver = new CustomSigningKeyResolver(secretKey);

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKeyResolver(keyResolver)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isUserValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

    public boolean isTokenNonExpired(String token) {
        return extractExpiration(token).after(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return (isUserValid(token, userDetails) && isTokenNonExpired(token));
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
                .signWith((Key) keyResolver, SignatureAlgorithm.HS256)
                .compact();
    }

}
