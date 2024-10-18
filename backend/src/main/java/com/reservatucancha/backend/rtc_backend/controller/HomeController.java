package com.reservatucancha.backend.rtc_backend.controller;


import com.reservatucancha.backend.rtc_backend.dto.response.PitchHomeResponseDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.PitchResponseDTO;
import com.reservatucancha.backend.rtc_backend.service.IPitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    private final IPitchService pitchService;

    @Autowired

    public HomeController(IPitchService pitchService) {
        this.pitchService = pitchService;
    }

    @GetMapping
    public ResponseEntity<List<PitchHomeResponseDTO>> getAllPitches() {
        return  ResponseEntity.ok(pitchService.getAllPitchesForHome());
    }
    @GetMapping("detail/{id}")
    public ResponseEntity<PitchResponseDTO> getPitchDetail(@PathVariable Long id) {
        return  ResponseEntity.ok(pitchService.getPitchById(id));
    }
}
