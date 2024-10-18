package com.reservatucancha.backend.rtc_backend.repository;

import com.reservatucancha.backend.rtc_backend.entity.Schedule.Days;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDaysRepository extends JpaRepository<Days,Long> {
}
