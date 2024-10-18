package com.reservatucancha.backend.rtc_backend.entity;
import com.reservatucancha.backend.rtc_backend.entity.Schedule.ScheduleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Pitch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pitch")
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String pitchName;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String pitchDescription;

    @Column(columnDefinition = "TEXT")
    private String pitchLocation;

    @Column(columnDefinition = "TEXT")
    private String pitchAddress;

    @OneToOne(optional = false,cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "map_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MapsEntity pitchMapUrl;

    @Column
    private double pitchPricePerHour;

    @OneToMany(mappedBy = "pitches",fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ScheduleEntity> schedules = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "pitch_service",
            joinColumns = @JoinColumn(name = "pitch_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Services> services = new HashSet<>();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "surface_id", nullable = false)
    private Surface surface;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sport_id", nullable = false)
    private Sport sport;

    @ElementCollection
    private List<String> imagePaths;

}
