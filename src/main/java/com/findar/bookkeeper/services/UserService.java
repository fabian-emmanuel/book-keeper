package com.findar.bookkeeper.services;

import com.findar.bookkeeper.dtos.UserRequestDTO;
import com.findar.bookkeeper.dtos.UserResponseDTO;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponseDTO> registerUser(UserRequestDTO userRequestDTO);
}
