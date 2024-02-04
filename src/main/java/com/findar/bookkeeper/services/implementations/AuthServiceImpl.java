package com.findar.bookkeeper.services.implementations;

import com.findar.bookkeeper.dtos.LoginRequestDTO;
import com.findar.bookkeeper.dtos.TokenResponseDTO;
import com.findar.bookkeeper.exceptions.InvalidRequestException;
import com.findar.bookkeeper.services.AuthService;
import com.findar.bookkeeper.services.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.findar.bookkeeper.utils.SecurityUtil.extractUserRole;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ReactiveAuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;

    @Override
    public Mono<TokenResponseDTO> login(LoginRequestDTO loginRequest) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()))
                .flatMap(authentication -> authTokenService.generateAccessToken(authentication.getName(), extractUserRole(authentication)))
                .doOnError(throwable -> {
                    throw new InvalidRequestException(throwable.getMessage());
                });
    }

}
