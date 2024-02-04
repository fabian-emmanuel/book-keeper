package com.findar.bookkeeper.services.implementations;

import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.dtos.UserRequestDTO;
import com.findar.bookkeeper.dtos.UserResponseDTO;
import com.findar.bookkeeper.exceptions.DuplicateException;
import com.findar.bookkeeper.repositories.UserRepository;
import com.findar.bookkeeper.services.UserService;
import com.findar.bookkeeper.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public Mono<UserResponseDTO> registerUser(UserRequestDTO userRequestDTO) {
        return userRepository.existsByEmailIgnoreCase(userRequestDTO.email())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new DuplicateException(String.format(Message.USER_WITH_EMAIL_ALREADY_EXIST, userRequestDTO.email())));
                    }
                    return userRepository.save(UserMapper.toUser(userRequestDTO, encoder))
                            .map(UserMapper::toUserResponseDTO);
                });
    }
}
