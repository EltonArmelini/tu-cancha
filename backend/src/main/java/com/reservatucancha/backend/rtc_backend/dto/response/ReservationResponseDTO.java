package com.reservatucancha.backend.rtc_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import java.time.LocalTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class ReservationResponseDTO{
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Float fullPrice;
    private Long userId;
    private Long pitchId;
}
