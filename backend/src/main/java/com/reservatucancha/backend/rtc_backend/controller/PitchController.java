package com.reservatucancha.backend.rtc_backend.controller;
import com.reservatucancha.backend.rtc_backend.dto.request.PitchRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.PitchResponseDTO;
import com.reservatucancha.backend.rtc_backend.service.IPitchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/operator/pitches")
public class PitchController {

    private final IPitchService pitchService;

    @Autowired
    public PitchController(IPitchService pitchService) {
        this.pitchService = pitchService;
    }

    // Obtener todas las canchas
    @GetMapping
        public ResponseEntity<List<PitchResponseDTO>> getAllPitches() {
        return ResponseEntity.ok(pitchService.getAllPitches());
    }
    // Obtener una cancha por ID
    @GetMapping("/{id}")
    public ResponseEntity<PitchResponseDTO> getPitchById(@PathVariable Long id) {
        PitchResponseDTO pitch = pitchService.getPitchById(id);
        return pitch != null ? ResponseEntity.ok(pitch) :
                ResponseEntity.notFound().build();
    }

    // Crear una nueva cancha
    @PostMapping
    public ResponseEntity<PitchResponseDTO> createPitch(@Validated @RequestBody PitchRequestDTO pitchRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(pitchService.createPitch(pitchRequest));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<String> PitchFileUpload(@PathVariable("id") Long id, @RequestParam("file")List<MultipartFile> files){
        List<String> uploadedImagePaths = pitchService.PitchFileUpload(id, files);
        return ResponseEntity.ok("Successfully added the images" + uploadedImagePaths);
    }

    // Actualizar una cancha existente
    @PutMapping("/{id}")

    public ResponseEntity<PitchResponseDTO> updatePitch(@PathVariable Long id, @Validated @RequestBody PitchRequestDTO pitchRequest){
            PitchResponseDTO updatedPitch = pitchService.updatePitch(id, pitchRequest);
            return ResponseEntity.ok(updatedPitch);
    }

    @PutMapping("/{id}/images")
    public ResponseEntity<String> PitchFileUpdate(@PathVariable("id") Long id, @RequestParam("file")List<MultipartFile> files){
        List<String> uploadedImagePaths = pitchService.PitchFileUpdate(id, files);
        return ResponseEntity.ok("Successfully updated the images" + uploadedImagePaths);
    }

    // Eliminar una cancha
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePitch(@PathVariable Long id) {
        pitchService.deletePitch(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener canchas por superficie
    @GetMapping("/surface/{surfaceId}")
    public ResponseEntity<List<PitchResponseDTO>> getPitchesBySurface(@PathVariable Long surfaceId) {
        List<PitchResponseDTO> pitches = pitchService.getPitchesBySurface(surfaceId);
        return pitches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pitches);
    }

    // Filtrar por location
    @GetMapping("/by-location")
    public ResponseEntity<List<PitchResponseDTO>> getPitchesByLocation(@RequestParam("location") String location) {
        List<PitchResponseDTO> pitches = pitchService.getPitchesByLocation(location);
        return ResponseEntity.ok(pitches);
    }

    // Filtrar por sport
    @GetMapping("/by-sport")
    public ResponseEntity<List<PitchResponseDTO>> getPitchesBySport(@RequestParam("sportId") Long sportId) {
        List<PitchResponseDTO> pitches = pitchService.getPitchesBySport(sportId);
        return ResponseEntity.ok(pitches);
    }

    // Filtrar por location y sport
    @GetMapping("/by-location-and-sport")
    public ResponseEntity<List<PitchResponseDTO>> getPitchesByLocationAndSport(
            @RequestParam("location") String location,
            @RequestParam("sportId") Long sportId) {
        List<PitchResponseDTO> pitches = pitchService.getPitchesByLocationAndSport(location, sportId);
        return ResponseEntity.ok(pitches);
    }
}
