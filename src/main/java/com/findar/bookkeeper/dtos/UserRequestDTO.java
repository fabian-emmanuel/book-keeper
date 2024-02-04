package com.findar.bookkeeper.dtos;

import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.constants.Regex;
import com.findar.bookkeeper.enums.Role;
import com.findar.bookkeeper.validators.Enum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;


@Builder
public record UserRequestDTO(
        @NotBlank(message = Message.FIRST_NAME_IS_REQUIRED)
        String firstName,

        @NotBlank(message = Message.LAST_NAME_IS_REQUIRED)
        String lastName,

        @NotBlank(message = Message.EMAIL_IS_REQUIRED)
        @Email(message = Message.INVALID_EMAIL, flags = Pattern.Flag.CASE_INSENSITIVE)
        String email,

        @NotBlank(message = Message.PASSWORD_IS_REQUIRED)
        @Pattern(regexp = Regex.VALID_PASSWORD, message = Message.INVALID_PASSWORD)
        String password,

        @Enum(enumClass = Role.class, message = Message.ROLE_ALLOWABLE_VALUES)
        String role
) {
}