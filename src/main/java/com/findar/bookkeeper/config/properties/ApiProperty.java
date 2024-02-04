package com.findar.bookkeeper.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
public record ApiProperty(
        String moduleName,
        String version,
        String appName,
        String appSupportEmail,
        String appWebsite
) {}
