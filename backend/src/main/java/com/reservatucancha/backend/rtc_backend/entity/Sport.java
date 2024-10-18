package com.reservatucancha.backend.rtc_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SPORTS")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSport;
    @Column(length = 50)
    private String sportName;

    @OneToMany(mappedBy = "sport")
    private Set<Pitch> pitch;
}




