package com.reservatucancha.backend.rtc_backend.service.implementation;

import com.reservatucancha.backend.rtc_backend.dto.request.MapRequestDTO;
import com.reservatucancha.backend.rtc_backend.entity.MapsEntity;
import com.reservatucancha.backend.rtc_backend.exception.ResourceNotFoundException;
import com.reservatucancha.backend.rtc_backend.exception.UniqueConstraintViolationException;
import com.reservatucancha.backend.rtc_backend.repository.IMapEntityRepository;
import com.reservatucancha.backend.rtc_backend.service.IMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MapServiceImplementation implements IMapService {

    private final IMapEntityRepository iMapEntityRepository;

    @Autowired
    public MapServiceImplementation(IMapEntityRepository iMapEntityRepository) {
        this.iMapEntityRepository = iMapEntityRepository;
    }

    @Override
    public MapsEntity createMap(MapRequestDTO map) {
        if (iMapEntityRepository.findByMapUrl(map.getMapUrl()).isEmpty()) {
            MapsEntity mapsEntity = new MapsEntity();
            mapsEntity.setMapLatitude(map.getMapLatitude());
            mapsEntity.setMapLongitude(map.getMapLongitude());
            mapsEntity.setMapUrl(map.getMapUrl());
            return iMapEntityRepository.save(mapsEntity);
        } else {
            throw new UniqueConstraintViolationException("Map URL exist!");
        }
    }

    @Override
    public MapsEntity updateMap(MapRequestDTO mapRequestDTO) {
        MapsEntity map =
                iMapEntityRepository.findByMapUrl(mapRequestDTO.getMapUrl()).orElse(new MapsEntity());
        map.setMapLatitude(mapRequestDTO.getMapLatitude());
        map.setMapLongitude(mapRequestDTO.getMapLongitude());
        map.setMapUrl(mapRequestDTO.getMapUrl());
        return iMapEntityRepository.save(map);
    }
}
