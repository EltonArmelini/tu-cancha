package com.reservatucancha.backend.rtc_backend.repository;

import com.reservatucancha.backend.rtc_backend.entity.MapsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IMapEntityRepository extends JpaRepository<MapsEntity,Long> {
    Optional<MapsEntity> findByMapUrl(String mapUrl);
}
