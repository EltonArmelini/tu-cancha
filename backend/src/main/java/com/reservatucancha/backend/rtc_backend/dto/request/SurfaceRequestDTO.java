package com.reservatucancha.backend.rtc_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record SurfaceRequestDTO(

        @NotBlank
        @NotNull
        String name
) {
    public String getName() {
        return name;
    }
    //
}
