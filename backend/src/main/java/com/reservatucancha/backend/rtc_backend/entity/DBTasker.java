package com.reservatucancha.backend.rtc_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "db_tasker")@NoArgsConstructor @Getter @Setter
public class DBTasker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "script_name", nullable = false, unique = true)
    private String scriptName;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "executed", nullable = false)
    private Boolean executed;
}
