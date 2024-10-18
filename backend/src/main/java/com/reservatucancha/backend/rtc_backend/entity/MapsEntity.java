package com.reservatucancha.backend.rtc_backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "pitch_map")
@AllArgsConstructor @NoArgsConstructor @Data
public class MapsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMap;

    @Column(nullable = false,unique = true)
    private String mapUrl;

    @Column(nullable = false)
    private String mapLatitude;

    @Column(nullable = false)
    private String mapLongitude;

}
