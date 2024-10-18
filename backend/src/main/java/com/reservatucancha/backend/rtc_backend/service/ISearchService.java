package com.reservatucancha.backend.rtc_backend.service;

import com.reservatucancha.backend.rtc_backend.dto.response.PitchResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ISearchService {

    List<PitchResponseDTO> search(String location, String sport);
}
