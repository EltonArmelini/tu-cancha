package com.reservatucancha.backend.rtc_backend.controller;
import com.reservatucancha.backend.rtc_backend.dto.request.SurfaceRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.SurfaceResponseDTO;
import com.reservatucancha.backend.rtc_backend.service.ISurfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/operator/surfaces")
public class SurfaceController {


    ISurfaceService surfaceService;

    @Autowired
    public SurfaceController(ISurfaceService surfaceService) {
        this.surfaceService = surfaceService;
    }

    // Obtener todas las superficies
    @GetMapping
    public List<SurfaceResponseDTO> getAllSurfaces() {
        return surfaceService.getAllSurfaces();
    }

    // Obtener una superficie por ID
    @GetMapping("/{id}")
    public ResponseEntity<SurfaceResponseDTO> getSurfaceById(@PathVariable Long id) {
        SurfaceResponseDTO surface = surfaceService.getSurfaceById(id);
        return surface != null ? ResponseEntity.ok(surface) : ResponseEntity.notFound().build();
    }

    // Crear una nueva superficie
    @PostMapping
    public ResponseEntity<SurfaceResponseDTO> createSurface(@RequestBody SurfaceRequestDTO surfaceRequest) {
        SurfaceResponseDTO responseDto = surfaceService.createSurface(surfaceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // Actualizar una superficie existente
    @PutMapping("/{id}")
    public ResponseEntity<SurfaceResponseDTO> updateSurface(@PathVariable Long id, @RequestBody SurfaceRequestDTO surfaceRequest) {
        SurfaceResponseDTO responseDto = surfaceService.updateSurface(id, surfaceRequest);
        return responseDto != null ? ResponseEntity.ok(responseDto) : ResponseEntity.notFound().build();
    }

    // Eliminar una superficie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurface(@PathVariable Long id) {
        surfaceService.deleteSurface(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SurfaceResponseDTO>> searchSurfacesByName(@RequestParam String name) {
        List<SurfaceResponseDTO> surfaces = surfaceService.searchSurfacesByName(name);
        return surfaces.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(surfaces);
    }

}
