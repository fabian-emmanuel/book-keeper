package com.findar.bookkeeper.config;

import com.findar.bookkeeper.services.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final AuthTokenService authTokenService;

    @Bean
    ReactiveAuditorAware<String> auditorAware() {
        return authTokenService::getAuthenticatedUserEmail;
    }

    @Bean
    public R2dbcMappingContext r2dbcMappingContext() {
        return new R2dbcMappingContext();
    }

}
