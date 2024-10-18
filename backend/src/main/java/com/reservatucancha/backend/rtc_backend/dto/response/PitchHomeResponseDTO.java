package com.reservatucancha.backend.rtc_backend.dto.response;


import com.reservatucancha.backend.rtc_backend.entity.Sport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class PitchHomeResponseDTO {
    private Long id;
    private String pitchName;
    private String pitchAddress;
    private String pitchDescription;
    private String pitchLocation;
    private SportResponseDTO sport;
    private List<String> imagePaths;
}
