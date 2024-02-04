package com.findar.bookkeeper.dtos;

import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.enums.Status;
import com.findar.bookkeeper.validators.Enum;
import lombok.Builder;

@Builder
public record FilterParam(
        Long page,
        Long size,
        @Enum(enumClass = Status.class, message = Message.STATUS_ALLOWABLE_VALUES)
        String status
) {
}
