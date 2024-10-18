package com.reservatucancha.backend.rtc_backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ServicesResponseDTO {
    private Long id;
    private String serviceName;
}
