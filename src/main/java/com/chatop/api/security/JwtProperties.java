package com.chatop.api.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtProperties {
    @Value("${security.secretkey}")
    private String secretKey;
}
