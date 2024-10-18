package com.reservatucancha.backend.rtc_backend.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data @NoArgsConstructor
public class BookingConfirmation {

    private String userEmail;
    private String clientName;
    private String pitchName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String pitchAddress;
}
