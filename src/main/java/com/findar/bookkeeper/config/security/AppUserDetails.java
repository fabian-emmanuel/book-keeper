package com.findar.bookkeeper.config.security;

import com.findar.bookkeeper.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserDetails implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepository.findUserByEmailIgnoreCase(email)
                .map(u -> User.withUsername(u.getEmail())
                         .password(u.getPassword())
                         .accountExpired(!u.isActive())
                         .disabled(!u.isActive())
                         .accountLocked(!u.isActive())
                         .roles(u.getUserRole().name())
                         .build()
                );
    }
}
