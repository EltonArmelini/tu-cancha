package com.reservatucancha.backend.rtc_backend.service;

import com.reservatucancha.backend.rtc_backend.dto.request.RatingRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.RatingResponseDTO;

import java.util.List;

public interface IRatingService {

    RatingResponseDTO createRating(RatingRequestDTO ratingRequestDTO);

    RatingResponseDTO getRatingById(Long id);

    List<RatingResponseDTO> getAllRatings();

    void deleteRating(Long id);

    List<RatingResponseDTO> getRatingsByUserId(Long userId);

    List<RatingResponseDTO> getRatingsByPitchId(Long pitchId);
}
