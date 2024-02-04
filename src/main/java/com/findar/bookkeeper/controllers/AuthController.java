package com.findar.bookkeeper.controllers;


import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.dtos.BaseResponse;
import com.findar.bookkeeper.dtos.LoginRequestDTO;
import com.findar.bookkeeper.dtos.TokenResponseDTO;
import com.findar.bookkeeper.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping( "/login")
    public Mono<BaseResponse<TokenResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO)
                .map(loginResponse -> new BaseResponse<>(true, HttpStatus.OK, Message.USER_LOGGED_IN_SUCCESSFULLY, loginResponse));
    }
}
