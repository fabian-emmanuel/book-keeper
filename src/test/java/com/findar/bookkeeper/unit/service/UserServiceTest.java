package com.findar.bookkeeper.unit.service;

import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.dtos.UserRequestDTO;
import com.findar.bookkeeper.enums.Role;
import com.findar.bookkeeper.exceptions.DuplicateException;
import com.findar.bookkeeper.mappers.UserMapper;
import com.findar.bookkeeper.models.User;
import com.findar.bookkeeper.repositories.UserRepository;
import com.findar.bookkeeper.services.implementations.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void shouldRegisterUserSuccessfully() {
        UserRequestDTO request = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("healing@findar.com")
                .password("password")
                .build();

        User user = UserMapper.toUser(request, passwordEncoder);
        user.setId(1L);
        user.setCreatedAt(LocalDateTime.now());

        when(userRepository.existsByEmailIgnoreCase(anyString())).thenReturn(Mono.just(false));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(userService.registerUser(request))
                .expectNextMatches(response -> {
                    assertNotNull(response.userId());
                    assertEquals(request.firstName(), response.firstName());
                    assertEquals(request.lastName(), response.lastName());
                    assertEquals(request.email(), response.email());
                    assertEquals(Role.USER, response.role());
                    assertTrue(response.active());
                    assertNotNull(response.createdAt());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(userRepository, times(1)).existsByEmailIgnoreCase(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldReturnDuplicateExceptionWhenRegisterUserAndUserWithRequestEmailExist() {

        UserRequestDTO request = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("healing@findar.com")
                .password("password")
                .build();

        when(userRepository.existsByEmailIgnoreCase(anyString())).thenReturn(Mono.just(true));

        StepVerifier.create(userService.registerUser(request))
                .expectErrorSatisfies(response -> assertThat(response)
                        .isInstanceOf(DuplicateException.class)
                        .hasMessage(String.format(Message.USER_WITH_EMAIL_ALREADY_EXIST, request.email())))
                .verify();

        verify(userRepository, times(1)).existsByEmailIgnoreCase(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
