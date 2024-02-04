package com.findar.bookkeeper.dtos;

import lombok.Builder;

@Builder
public record BookRequestDTO(
        String title,
        String author
) {
}
