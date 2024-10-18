package com.reservatucancha.backend.rtc_backend.dto.response;

public record RatingResponseDTO(
        Long id,
        double rate,
        Long userId,
        String userName,
        Long pitchId,
        String pitchName
) {
}