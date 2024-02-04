package com.findar.bookkeeper.controllers;

import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.dtos.BaseResponse;
import com.findar.bookkeeper.dtos.UserRequestDTO;
import com.findar.bookkeeper.dtos.UserResponseDTO;
import com.findar.bookkeeper.services.UserService;
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
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping( "/register")
    public Mono<BaseResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.registerUser(userRequestDTO)
                .map(user -> new BaseResponse<>(true, HttpStatus.CREATED, Message.USER_REGISTERED_SUCCESSFULLY, user));
    }
}
