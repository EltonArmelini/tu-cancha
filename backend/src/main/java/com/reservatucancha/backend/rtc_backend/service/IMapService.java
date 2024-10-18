package com.reservatucancha.backend.rtc_backend.service;

import com.reservatucancha.backend.rtc_backend.dto.request.MapRequestDTO;
import com.reservatucancha.backend.rtc_backend.entity.MapsEntity;

public interface IMapService {

    MapsEntity createMap(MapRequestDTO mapRequestDTO);
    MapsEntity updateMap(MapRequestDTO mapRequestDTO);

}
