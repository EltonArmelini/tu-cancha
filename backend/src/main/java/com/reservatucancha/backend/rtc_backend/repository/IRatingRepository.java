package com.reservatucancha.backend.rtc_backend.repository;

import com.reservatucancha.backend.rtc_backend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByUserId(Long userId);

    List<Rating> findByPitchId(Long pitchId);
}
