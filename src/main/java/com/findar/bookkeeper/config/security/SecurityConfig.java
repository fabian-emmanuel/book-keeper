package com.findar.bookkeeper.config.security;

import com.findar.bookkeeper.constants.Security;
import com.findar.bookkeeper.exceptions.CustomAccessDeniedExceptionHandler;
import com.findar.bookkeeper.exceptions.CustomAuthenticationEntryPointHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Slf4j
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAccessDeniedExceptionHandler unauthorizedExceptionHandler;
    private final CustomAuthenticationEntryPointHandler authenticationEntryPointHandler;
    private final CustomSecurityContextRepository customSecurityContextRepository;

    @Bean
    public SecurityWebFilterChain webSecurityFilter(ServerHttpSecurity http) {
        return http.cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(Security.WHITE_LISTED_PATH.toArray(String[]::new))
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                .securityContextRepository(customSecurityContextRepository)
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint(authenticationEntryPointHandler)
                        .accessDeniedHandler(unauthorizedExceptionHandler)
                ).logout(logoutSpec -> logoutSpec.logoutUrl("/logout"))
                .build();
    }
}
