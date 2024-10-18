package com.reservatucancha.backend.rtc_backend.repository;

import com.reservatucancha.backend.rtc_backend.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISportRepository extends JpaRepository<Sport,Long> {
    Optional<Sport> findBySportName(String name);
}
