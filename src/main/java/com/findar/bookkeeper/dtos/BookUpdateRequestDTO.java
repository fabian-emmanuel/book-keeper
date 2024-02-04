package com.findar.bookkeeper.dtos;

import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.enums.Status;
import com.findar.bookkeeper.validators.Enum;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record BookUpdateRequestDTO(
        @Positive(message = Message.BOOK_ID_IS_REQUIRED)
        Long bookId,
        String title,
        String author,
        @Enum(enumClass = Status.class, message = Message.STATUS_ALLOWABLE_VALUES)
        String status
) {
}
