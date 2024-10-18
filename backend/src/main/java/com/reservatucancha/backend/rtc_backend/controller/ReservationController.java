package com.reservatucancha.backend.rtc_backend.controller;

import com.reservatucancha.backend.rtc_backend.dto.request.ReservationRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.ReservationResponseDTO;
import com.reservatucancha.backend.rtc_backend.service.IEmailService;
import com.reservatucancha.backend.rtc_backend.service.IPitchService;
import com.reservatucancha.backend.rtc_backend.service.IReservationService;
import com.reservatucancha.backend.rtc_backend.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients/reservations")
public class ReservationController {



    private final IReservationService reservationService;

    @Autowired
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return ResponseEntity.ok( reservationService.createReservation(reservationRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByUserId(@PathVariable Long userId) {
         return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @GetMapping("/pitch/{pitchId}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByPitchId(@PathVariable Long pitchId) {
        return ResponseEntity.ok(reservationService.getReservationsByPitchId(pitchId));
    }

}
