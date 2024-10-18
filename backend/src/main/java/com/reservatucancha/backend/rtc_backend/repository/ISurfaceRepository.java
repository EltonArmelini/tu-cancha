package com.reservatucancha.backend.rtc_backend.repository;

import com.reservatucancha.backend.rtc_backend.entity.Surface;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISurfaceRepository extends JpaRepository<Surface, Long> {

    //este metodo encuentra la superficie por nombre parcial o completo
    List<Surface> findBySurfaceNameContainingIgnoreCase(String name);
}
