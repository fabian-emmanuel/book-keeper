package com.findar.bookkeeper.constants;

import java.util.List;

public interface Security {
    String USER_ROLE = "role";

    List<String> WHITE_LISTED_PATH = List.of(
            "/v3/api-docs/**",
            "/webjars/**",
            "/actuator/**",
            "/auth/**",
            "/users/**"
    );
}
