package com.reservatucancha.backend.rtc_backend.entity;


import com.reservatucancha.backend.rtc_backend.entity.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalTime startTime;

    @Column
    private LocalDate date;

    @Column
    private LocalTime endTime;

    @Column
    private Float fullPrice;

    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "Id_Pitch", nullable = false)
    private Pitch pitch;

}
