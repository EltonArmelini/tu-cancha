package com.reservatucancha.backend.rtc_backend.service.implementation;
import com.reservatucancha.backend.rtc_backend.dto.request.SurfaceRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.SurfaceResponseDTO;
import com.reservatucancha.backend.rtc_backend.entity.Surface;
import com.reservatucancha.backend.rtc_backend.repository.ISurfaceRepository;
import com.reservatucancha.backend.rtc_backend.service.ISurfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurfaceServiceImplementation implements ISurfaceService {

    ISurfaceRepository ISurfaceRepository;

    @Autowired
    public SurfaceServiceImplementation(ISurfaceRepository ISurfaceRepository) {
        this.ISurfaceRepository = ISurfaceRepository;
    }

    @Override
    public List<SurfaceResponseDTO> getAllSurfaces() {
        return ISurfaceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SurfaceResponseDTO getSurfaceById(Long id) {
        return ISurfaceRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public SurfaceResponseDTO createSurface(SurfaceRequestDTO surfaceRequest) {
        Surface surface = new Surface();
        surface.setSurfaceName(surfaceRequest.getName());

        Surface savedSurface = ISurfaceRepository.save(surface);
        return mapToDto(savedSurface);
    }

    @Override
    public SurfaceResponseDTO updateSurface(Long id, SurfaceRequestDTO surfaceRequest) {
        return ISurfaceRepository.findById(id)
                .map(surface -> {
                    surface.setSurfaceName(surfaceRequest.getName());
                    Surface updatedSurface = ISurfaceRepository.save(surface);
                    return mapToDto(updatedSurface);
                })
                .orElse(null);
    }

    @Override
    public void deleteSurface(Long id) {
        ISurfaceRepository.deleteById(id);
    }

    @Override
    public List<SurfaceResponseDTO> searchSurfacesByName(String name) {
        return ISurfaceRepository.findBySurfaceNameContainingIgnoreCase(name).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SurfaceResponseDTO mapToDto(Surface surface) {
        SurfaceResponseDTO dto = new SurfaceResponseDTO();
        dto.setId(surface.getIdSurface());
        dto.setName(surface.getSurfaceName());
        return dto;
    }
}
