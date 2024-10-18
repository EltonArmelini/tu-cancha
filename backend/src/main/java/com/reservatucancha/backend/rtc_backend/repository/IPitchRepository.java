package com.reservatucancha.backend.rtc_backend.repository;

import com.reservatucancha.backend.rtc_backend.entity.Pitch;
import com.reservatucancha.backend.rtc_backend.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPitchRepository extends JpaRepository<Pitch, Long> {

    Optional<Pitch> findPitchByPitchName(String name);
    void deleteByPitchName(String name);
    List<Pitch> findBySport(Sport sport);
    List<Pitch> findByPitchLocationContaining(String pitchLocation);
    List<Pitch> findByPitchLocationAndSport(String pitchLocation, Sport sport);
    List<Pitch> findByPitchLocationContainingAndSportSportName(String location, String sportName);
}
