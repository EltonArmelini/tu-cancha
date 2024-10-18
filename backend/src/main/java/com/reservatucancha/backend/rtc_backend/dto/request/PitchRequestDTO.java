package com.reservatucancha.backend.rtc_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PitchRequestDTO {

    @NotNull(message = "The name of the pitch can't be null")
    @NotBlank(message = "The name of the pitch must be specified")
    private String pitchName;

    @NotNull(message = "The description of the pitch can't be null")
    @NotBlank(message = "The description of the pitch must be specified")
    private String pitchDescription;

    @Positive(message = "The price of the pitch can't be null and must be greater than zero.")
    private double pitchPricePerHour;

    @NotNull(message = "The sport id can't be null.")
    private Long idSport;

    @NotNull(message = "The location of the pitch can't be null")
    @NotBlank(message = "The location of the pitch must be specified")
    private String pitchLocation;

    @NotNull(message = "The address of the pitch can't be null")
    @NotBlank(message = "The address of the pitch must be specified")
    private String pitchAddress;

    @NotNull(message = "The map url of the pitch can't be null")
    @NotBlank(message = "The map url of the pitch must be specified")
    private MapRequestDTO pitchMapUrl;

    @NotNull(message = "The surface id can't be null.")
    private Long idSurface;

    @NotNull(message = "The schedules can't be null.")
    @NotBlank(message = "The schedules of the pitch must be specified")
    private Set<ScheduleRequestDTO> schedules;

    @NotNull(message = "The services can't be null.")
    private Set<Long> idServices;
}
