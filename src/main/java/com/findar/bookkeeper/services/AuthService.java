package com.findar.bookkeeper.services;

import com.findar.bookkeeper.dtos.LoginRequestDTO;
import com.findar.bookkeeper.dtos.TokenResponseDTO;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<TokenResponseDTO> login(LoginRequestDTO loginRequest);
}
