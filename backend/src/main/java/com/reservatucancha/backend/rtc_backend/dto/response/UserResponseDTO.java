package com.reservatucancha.backend.rtc_backend.dto.response;

public record UserResponseDTO(
        String email,
        String firstName,
        String lastName,
        String roles
) {}
