package com.reservatucancha.backend.rtc_backend.service.implementation;

import com.reservatucancha.backend.rtc_backend.dto.request.RatingRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.RatingResponseDTO;
import com.reservatucancha.backend.rtc_backend.entity.Pitch;
import com.reservatucancha.backend.rtc_backend.entity.Rating;
import com.reservatucancha.backend.rtc_backend.entity.User.UserEntity;
import com.reservatucancha.backend.rtc_backend.exception.ResourceNotFoundException;
import com.reservatucancha.backend.rtc_backend.repository.IPitchRepository;
import com.reservatucancha.backend.rtc_backend.repository.IRatingRepository;
import com.reservatucancha.backend.rtc_backend.repository.IUserRepository;
import com.reservatucancha.backend.rtc_backend.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImplementation implements IRatingService {

    @Autowired
    private IRatingRepository IRatingRepository;

    @Autowired
    private IUserRepository IUserRepository;

    @Autowired
    private IPitchRepository IPitchRepository;

    @Override
    public RatingResponseDTO createRating(RatingRequestDTO ratingRequestDTO) {
        Rating rating = new Rating();
        rating.setRate(ratingRequestDTO.rate());

        // Find the user and pitch by their IDs
        UserEntity user = IUserRepository.findById(ratingRequestDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + ratingRequestDTO.userId() + " not found"));

        Pitch pitch = IPitchRepository.findById(ratingRequestDTO.pitchId())
                .orElseThrow(() -> new ResourceNotFoundException("Pitch with ID " + ratingRequestDTO.pitchId() + " not found"));

        // Set the user and pitch for the rating
        rating.setUser(user);
        rating.setPitch(pitch);

        // Save the rating to the database
        Rating savedRating = IRatingRepository.save(rating);

        // Return RatingResponseDTO with additional userName and pitchName fields
        return new RatingResponseDTO(
                savedRating.getId(),
                savedRating.getRate(),
                savedRating.getUser().getId(),
                savedRating.getUser().getUserFirstName() + " " + savedRating.getUser().getUserLastName(),
                savedRating.getPitch().getId(),
                savedRating.getPitch().getPitchName()
        );
    }

    @Override
    public RatingResponseDTO getRatingById(Long id) {
        // Retrieve a rating by its ID or throw RatingNotFoundException
        Rating rating = IRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating with ID " + id + " not found"));

        // Map the Rating entity to RatingResponseDTO
        return new RatingResponseDTO(
                rating.getId(),
                rating.getRate(),
                rating.getUser().getId(),
                rating.getUser().getUserFirstName() + " " + rating.getUser().getUserLastName(),
                rating.getPitch().getId(),
                rating.getPitch().getPitchName()
        );
    }

    @Override
    public List<RatingResponseDTO> getAllRatings() {
        // Retrieve all ratings and map them to RatingResponseDTOs
        return IRatingRepository.findAll().stream()
                .map(rating -> new RatingResponseDTO(
                        rating.getId(),
                        rating.getRate(),
                        rating.getUser().getId(),
                        rating.getUser().getUserFirstName() + " " + rating.getUser().getUserLastName(),
                        rating.getPitch().getId(),
                        rating.getPitch().getPitchName()
                ))
                .toList();
    }

    @Override
    public void deleteRating(Long id) {
        // Check if the rating exists before deletion
        Rating rating = IRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating with ID " + id + " not found"));

        IRatingRepository.delete(rating);
    }

    @Override
    public List<RatingResponseDTO> getRatingsByUserId(Long userId) {
        // Retrieve ratings by user ID and map them to RatingResponseDTOs
        List<RatingResponseDTO> ratings = IRatingRepository.findByUserId(userId).stream()
                .map(rating -> new RatingResponseDTO(
                        rating.getId(),
                        rating.getRate(),
                        rating.getUser().getId(),
                        rating.getUser().getUserFirstName() + " " + rating.getUser().getUserLastName(),
                        rating.getPitch().getId(),
                        rating.getPitch().getPitchName()
                ))
                .toList();

        // Throw an exception if no ratings are found
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for user with ID " + userId);
        }

        return ratings;
    }

    @Override
    public List<RatingResponseDTO> getRatingsByPitchId(Long pitchId) {
        // Retrieve ratings by pitch ID and map them to RatingResponseDTOs
        List<RatingResponseDTO> ratings = IRatingRepository.findByPitchId(pitchId).stream()
                .map(rating -> new RatingResponseDTO(
                        rating.getId(),
                        rating.getRate(),
                        rating.getUser().getId(),
                        rating.getUser().getUserFirstName() + " " + rating.getUser().getUserLastName(),
                        rating.getPitch().getId(),
                        rating.getPitch().getPitchName()
                ))
                .toList();

        // Throw an exception if no ratings are found
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for pitch with ID " + pitchId);
        }

        return ratings;
    }
}