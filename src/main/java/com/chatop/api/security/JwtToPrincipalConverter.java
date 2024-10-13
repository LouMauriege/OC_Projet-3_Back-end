package com.chatop.api.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .userId(Long.valueOf(jwt.getSubject()))
                .name(jwt.getClaim("name").asString())
                .authorities(extractAuthoritiesFromClaims(jwt))
                .build();
    }

    public List<SimpleGrantedAuthority> extractAuthoritiesFromClaims(DecodedJWT jwt) {
        var claim = jwt.getClaim("name");
        if(claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
