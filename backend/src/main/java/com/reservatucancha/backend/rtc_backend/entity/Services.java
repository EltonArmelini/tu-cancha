package com.reservatucancha.backend.rtc_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private Long id;

    private String serviceName;

    @ManyToMany(mappedBy = "services")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Pitch> pitches = new HashSet<>();
}
