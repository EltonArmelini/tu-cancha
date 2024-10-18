package com.reservatucancha.backend.rtc_backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingRequestDTO (
        @Min(0) @NotNull double rate,
        @NotNull Long userId,
        @NotNull Long pitchId
) {
}
