package com.reservatucancha.backend.rtc_backend.dto.response;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"userId","userEmail","userFirstName","userLastName","role","message", "status", "jwt"})
public record AuthLoginResponse(
    Long userId,
    String userEmail,
    String userFirstName,
    String userLastName,
    String role,
    String message,
    String expired_at,
    String jwt,
    Boolean status
){}
