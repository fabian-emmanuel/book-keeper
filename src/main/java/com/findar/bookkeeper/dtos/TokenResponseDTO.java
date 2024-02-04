package com.findar.bookkeeper.dtos;

import lombok.Builder;

@Builder
public record TokenResponseDTO(
        String token,
        String expiresIn
) {
}
