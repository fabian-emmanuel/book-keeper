package com.findar.bookkeeper.dtos;

import com.findar.bookkeeper.enums.Status;
import lombok.Builder;

@Builder
public record BookResponseDTO(
        Long bookId,
        String title,
        String author,
        String publisher,
        String genre,
        String isbn,
        Status status,
        double price,
        String createdAt,
        String createdBy
) {
}
