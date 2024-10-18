package com.reservatucancha.backend.rtc_backend.dto.response;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data @AllArgsConstructor @NoArgsConstructor
public class PitchResponseDTO {
    private Long id;
    private String pitchName;
    private String pitchDescription;
    private String pitchLocation;
    private MapResponseDTO pitchMapUrl;
    private String pitchAddress;
    private String surfaceName;
    private double pitchPricePerHour;
    private Set<ScheduleResponseDTO> schedules;
    private SportResponseDTO sport;
    private Set<ServicesResponseDTO> services;
    private List<String> imagePaths;
}
