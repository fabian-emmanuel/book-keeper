package com.findar.bookkeeper.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JWTProperty(
        String secretKey,
        Long expiration
) {}
