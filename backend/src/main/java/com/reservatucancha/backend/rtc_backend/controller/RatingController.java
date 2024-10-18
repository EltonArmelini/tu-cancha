package com.reservatucancha.backend.rtc_backend.controller;

import com.reservatucancha.backend.rtc_backend.dto.request.RatingRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.RatingResponseDTO;
import com.reservatucancha.backend.rtc_backend.service.IRatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients/ratings")
public class RatingController {

    @Autowired
    private IRatingService IRatingService;

    // Endpoint to create a new rating
    @PostMapping
    public ResponseEntity<RatingResponseDTO> createRating(@Valid @RequestBody RatingRequestDTO ratingRequestDTO) {
        // Call the service to create the rating
        RatingResponseDTO createdRating = IRatingService.createRating(ratingRequestDTO);
        // Return a 200 OK response with the created rating details
        return ResponseEntity.ok(createdRating);
    }

    // Endpoint to get a specific rating by its ID
    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseDTO> getRatingById(@PathVariable Long id) {
        // Call the service to retrieve the rating by ID
        RatingResponseDTO rating = IRatingService.getRatingById(id);
        // Return a 200 OK response with the rating details
        return ResponseEntity.ok(rating);
    }

    // Endpoint to get a list of all ratings
    @GetMapping
    public ResponseEntity<List<RatingResponseDTO>> getAllRatings() {
        // Call the service to retrieve all ratings
        List<RatingResponseDTO> ratings = IRatingService.getAllRatings();
        // Return a 200 OK response with the list of ratings
        return ResponseEntity.ok(ratings);
    }

    // Endpoint to delete a rating by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        // Call the service to delete the rating by ID
        IRatingService.deleteRating(id);
        // Return a 204 No Content response after successfully deleting the rating
        return ResponseEntity.noContent().build();
    }

    // Endpoint to get a list of ratings by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingResponseDTO>> getRatingsByUserId(@PathVariable Long userId) {
        // Call the service to retrieve ratings associated with a specific user
        List<RatingResponseDTO> ratings = IRatingService.getRatingsByUserId(userId);
        // Return a 200 OK response with the list of ratings for the user
        return ResponseEntity.ok(ratings);
    }

    // Endpoint to get a list of ratings by pitch ID
    @GetMapping("/pitch/{pitchId}")
    public ResponseEntity<List<RatingResponseDTO>> getRatingsByPitchId(@PathVariable Long pitchId) {
        // Call the service to retrieve ratings associated with a specific pitch
        List<RatingResponseDTO> ratings = IRatingService.getRatingsByPitchId(pitchId);
        // Return a 200 OK response with the list of ratings for the pitch
        return ResponseEntity.ok(ratings);
    }
}
