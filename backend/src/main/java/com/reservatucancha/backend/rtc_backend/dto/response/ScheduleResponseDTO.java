    package com.reservatucancha.backend.rtc_backend.dto.response;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalTime;

    @Data @NoArgsConstructor @AllArgsConstructor
    public class ScheduleResponseDTO {
        String dayName;
        LocalTime openingTime;
        LocalTime closingTime;
    }
