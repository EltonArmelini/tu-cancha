package com.reservatucancha.backend.rtc_backend.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Surface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSurface;

    @Column(nullable = false, unique = true)
    private String surfaceName;

    @OneToMany(mappedBy = "surface")
    private Set<Pitch> pitch;


}
