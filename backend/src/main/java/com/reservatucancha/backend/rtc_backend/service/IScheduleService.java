package com.reservatucancha.backend.rtc_backend.service;

import com.reservatucancha.backend.rtc_backend.dto.request.ScheduleRequestDTO;
import com.reservatucancha.backend.rtc_backend.entity.Schedule.ScheduleEntity;

public interface IScheduleService {
    ScheduleEntity createSchedule(ScheduleRequestDTO schedule, Long idPitch);
    ScheduleEntity updateSchedule(ScheduleRequestDTO schedule, Long idPitch);
}
