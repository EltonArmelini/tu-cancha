package com.reservatucancha.backend.rtc_backend.repository;


import com.reservatucancha.backend.rtc_backend.entity.Schedule.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface IScheduleEntityRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByPitches_Id(Long id);
}
