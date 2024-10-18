package com.reservatucancha.backend.rtc_backend.service;

import com.reservatucancha.backend.rtc_backend.dto.request.ReservationRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.ReservationResponseDTO;

import java.util.List;

public interface IReservationService {
    ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO);

    ReservationResponseDTO getReservationById(Long id);

    List<ReservationResponseDTO> getAllReservations();

    void deleteReservation(Long id);

    List<ReservationResponseDTO> getReservationsByUserId(Long userId);

    List<ReservationResponseDTO> getReservationsByPitchId(Long pitchId);
}
