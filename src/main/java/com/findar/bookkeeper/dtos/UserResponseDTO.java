package com.findar.bookkeeper.dtos;

import com.findar.bookkeeper.enums.Role;
import lombok.Builder;

@Builder
public record UserResponseDTO(
        Long userId,
        String firstName,
        String lastName,
        String email,
        boolean active,
        String createdAt,
        Role role
) {}
