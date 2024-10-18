package com.reservatucancha.backend.rtc_backend.entity.Schedule;


import com.reservatucancha.backend.rtc_backend.entity.Pitch;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSchedule;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private Days day;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pitch_id", nullable = false)
    private Pitch pitches;

    private LocalTime openingTime;
    private LocalTime closingTime;
}
