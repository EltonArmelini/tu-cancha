package com.reservatucancha.backend.rtc_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class MapRequestDTO {
    @NotNull
    @NotBlank
    private String mapUrl;

    @NotNull
    @NotBlank
    private String mapLatitude;

    @NotNull
    @NotBlank
    private String mapLongitude;
}
