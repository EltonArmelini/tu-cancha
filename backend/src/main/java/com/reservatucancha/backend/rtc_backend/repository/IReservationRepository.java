package com.reservatucancha.backend.rtc_backend.repository;

import com.reservatucancha.backend.rtc_backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByPitchId(Long pitchId);
    boolean existsByPitchIdAndDateAndStartTime(Long pitchId, LocalDate date, LocalTime startTime);

}
