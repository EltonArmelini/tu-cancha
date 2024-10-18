package com.reservatucancha.backend.rtc_backend.dto.request;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@AllArgsConstructor @NoArgsConstructor @Data
public class ScheduleRequestDTO {

    @NotBlank(message = "The day id  must be specified.")
    @NotNull(message = "The day id can't be null.")
    private Long idDay;

    @NotNull(message = "The open time can't be null.")
    @NotBlank(message = "The open time must be specified.")
    private LocalTime open;

    @NotNull(message = "The close time can't be null.")
    @NotBlank(message = "The close time must be specified.")
    private LocalTime close;
}
