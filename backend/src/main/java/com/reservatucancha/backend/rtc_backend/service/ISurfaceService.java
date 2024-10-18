package com.reservatucancha.backend.rtc_backend.service;

import com.reservatucancha.backend.rtc_backend.dto.request.SurfaceRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.SurfaceResponseDTO;

import java.util.List;

public interface ISurfaceService {
    List<SurfaceResponseDTO> getAllSurfaces();
    SurfaceResponseDTO getSurfaceById(Long id);
    SurfaceResponseDTO createSurface(SurfaceRequestDTO surfaceRequest);
    SurfaceResponseDTO updateSurface(Long id, SurfaceRequestDTO surfaceRequest);
    void deleteSurface(Long id);
    List<SurfaceResponseDTO> searchSurfacesByName(String name);
}
