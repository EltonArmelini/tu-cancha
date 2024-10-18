package com.reservatucancha.backend.rtc_backend.service.implementation;

import com.reservatucancha.backend.rtc_backend.dto.request.ScheduleRequestDTO;
import com.reservatucancha.backend.rtc_backend.entity.Schedule.Days;
import com.reservatucancha.backend.rtc_backend.entity.Pitch;
import com.reservatucancha.backend.rtc_backend.entity.Schedule.ScheduleEntity;
import com.reservatucancha.backend.rtc_backend.exception.ResourceNotFoundException;
import com.reservatucancha.backend.rtc_backend.repository.IDaysRepository;
import com.reservatucancha.backend.rtc_backend.repository.IPitchRepository;
import com.reservatucancha.backend.rtc_backend.repository.IScheduleEntityRepository;
import com.reservatucancha.backend.rtc_backend.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImplementation implements IScheduleService {

    private final IScheduleEntityRepository iScheduleEntityRepository;
    private final IDaysRepository iDaysRepository;
    private final IPitchRepository iPitchRepository;

    @Autowired
    public ScheduleServiceImplementation(IScheduleEntityRepository iScheduleEntityRepository,IDaysRepository iDaysRepository,IPitchRepository iPitchRepository) {
        this.iScheduleEntityRepository = iScheduleEntityRepository;
        this.iDaysRepository = iDaysRepository;
        this.iPitchRepository = iPitchRepository;
    }

    @Override
    public ScheduleEntity createSchedule(ScheduleRequestDTO schedule, Long idPitch) {
        Days day = iDaysRepository.findById(schedule.getIdDay())
                .orElseThrow(() -> new ResourceNotFoundException("Day not found"));
        Pitch pitch = iPitchRepository.findById(idPitch)
                .orElseThrow(() -> new ResourceNotFoundException("Pitch not found"));
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDay(day);
        scheduleEntity.setPitches(pitch);
        scheduleEntity.setOpeningTime(schedule.getOpen());
        scheduleEntity.setClosingTime(schedule.getClose());
        return iScheduleEntityRepository.save(scheduleEntity);
    }
    @Override
    public ScheduleEntity updateSchedule(ScheduleRequestDTO schedule, Long idPitch) {
        Days day = iDaysRepository.findById(schedule.getIdDay())
                .orElseThrow(() -> new ResourceNotFoundException("Day not found"));
        Pitch pitch = iPitchRepository.findById(idPitch)
                .orElseThrow(() -> new ResourceNotFoundException("Pitch not found"));
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDay(day);
        scheduleEntity.setPitches(pitch);
        scheduleEntity.setOpeningTime(schedule.getOpen());
        scheduleEntity.setClosingTime(schedule.getClose());
        return iScheduleEntityRepository.save(scheduleEntity);
    }
}
