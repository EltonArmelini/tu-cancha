package com.reservatucancha.backend.rtc_backend.dto.request;

import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid userEmail") String email
) {}
