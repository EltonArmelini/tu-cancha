package com.reservatucancha.backend.rtc_backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MapResponseDTO {
    private Long idMap;
    private String mapUrl;
    private String mapLatitude;
    private String mapLongitude;
}
