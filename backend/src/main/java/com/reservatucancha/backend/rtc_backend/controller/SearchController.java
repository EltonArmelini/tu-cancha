package com.reservatucancha.backend.rtc_backend.controller;

import com.reservatucancha.backend.rtc_backend.dto.response.PitchResponseDTO;
import com.reservatucancha.backend.rtc_backend.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/search")
public class SearchController {


    private final ISearchService iSearchService;

    @Autowired
    public SearchController(ISearchService iSearchService) {
        this.iSearchService = iSearchService;
    }

    @GetMapping
    public ResponseEntity<List<PitchResponseDTO>> searchPitches(@RequestParam String location, @RequestParam(required = false)String sport) {
            return ResponseEntity.ok().body(iSearchService.search(location,sport));
    }
}
