package com.reservatucancha.backend.rtc_backend.dto.request;


import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank String userEmail,
        @NotBlank String userPassword
){}
