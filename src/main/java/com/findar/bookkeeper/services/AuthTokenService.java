package com.findar.bookkeeper.services;

import com.findar.bookkeeper.dtos.TokenResponseDTO;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface AuthTokenService {
    Mono<TokenResponseDTO> generateAccessToken(String userName, String userRole);
    Mono<String> getAuthenticatedUserEmail();
    String extractUserEmailFromToken(String token);
    Mono<String> extractTokenFromRequest(ServerWebExchange exchange);
    boolean validateToken(String token, String authenticatedUserName);
}
