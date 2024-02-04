package com.findar.bookkeeper.mappers;

import com.findar.bookkeeper.dtos.UserRequestDTO;
import com.findar.bookkeeper.dtos.UserResponseDTO;
import com.findar.bookkeeper.enums.Role;
import com.findar.bookkeeper.models.User;
import com.findar.bookkeeper.utils.DateUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserMapper {

    static User toUser(UserRequestDTO requestDTO, PasswordEncoder  encoder) {
        return User.builder()
                .firstName(requestDTO.firstName())
                .lastName(requestDTO.lastName())
                .email(requestDTO.email())
                .password(encoder.encode(requestDTO.password()))
                .active(true)
                .userRole(StringUtil.isNullOrEmpty(requestDTO.role()) ? Role.USER : Role.valueOf(requestDTO.role()))
                .createdBy(requestDTO.email())
                .updatedBy(requestDTO.email())
                .build();
    }

    static UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getUserRole())
                .userId(user.getId())
                .active(user.isActive())
                .createdAt(DateUtil.formatLocalDateTime(user.getCreatedAt()))
                .build();
    }
}
