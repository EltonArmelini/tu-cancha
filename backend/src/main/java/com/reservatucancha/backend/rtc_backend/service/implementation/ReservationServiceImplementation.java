package com.reservatucancha.backend.rtc_backend.service.implementation;


import com.reservatucancha.backend.rtc_backend.dto.request.ReservationRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.BookingConfirmation;
import com.reservatucancha.backend.rtc_backend.dto.response.ReservationResponseDTO;
import com.reservatucancha.backend.rtc_backend.entity.Pitch;
import com.reservatucancha.backend.rtc_backend.entity.Reservation;
import com.reservatucancha.backend.rtc_backend.entity.User.UserEntity;
import com.reservatucancha.backend.rtc_backend.exception.ReservationNotAvailableException;
import com.reservatucancha.backend.rtc_backend.exception.ResourceNotFoundException;
import com.reservatucancha.backend.rtc_backend.repository.IPitchRepository;
import com.reservatucancha.backend.rtc_backend.repository.IReservationRepository;
import com.reservatucancha.backend.rtc_backend.repository.IUserRepository;
import com.reservatucancha.backend.rtc_backend.service.IReservationService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImplementation implements IReservationService {

    private final IReservationRepository iReservationRepository;
    private final IUserRepository iUserRepository;
    private final IPitchRepository iPitchRepository;
    private final EmailServiceImplementation emailServiceImplementation;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservationServiceImplementation(IReservationRepository iReservationRepository, IUserRepository IUserRepository,IPitchRepository iPitchRepository, ModelMapper modelMapper,EmailServiceImplementation emailServiceImplementation ) {
        this.iReservationRepository = iReservationRepository;
        this.iUserRepository = IUserRepository;
        this.iPitchRepository = iPitchRepository;
        this.emailServiceImplementation = emailServiceImplementation;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO) {
        // Find the user and pitch by their IDs
        UserEntity user = iUserRepository.findById(reservationRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + reservationRequestDTO.getUserId()+ " not found"));

        Pitch pitch = iPitchRepository.findById(reservationRequestDTO.getPitchId())
                .orElseThrow(() -> new ResourceNotFoundException("Pitch with ID " + reservationRequestDTO.getPitchId() + " not found"));

        if(checkAvailability(pitch.getId(), reservationRequestDTO.getDate(),reservationRequestDTO.getStartTime())) {
            throw new ReservationNotAvailableException("The date and time are not available for booking!"+" Pitch: "+pitch.getId()+" Date: "+reservationRequestDTO.getDate()+" Time: "+reservationRequestDTO.getStartTime());
        }

        // Create and save the reservation
        Reservation reservation = new Reservation();
        reservation.setDate(reservationRequestDTO.getDate());
        reservation.setStartTime(reservationRequestDTO.getStartTime());
        reservation.setEndTime(reservationRequestDTO.getEndTime());
        reservation.setFullPrice(reservationRequestDTO.getFullPrice());
        reservation.setUser(user);
        reservation.setPitch(pitch);
        Reservation savedReservation = iReservationRepository.save(reservation);

        // Send confirmation email
        BookingConfirmation bookingConfirmation = new BookingConfirmation();
        bookingConfirmation.setUserEmail(user.getUserEmail());
        bookingConfirmation.setClientName(user.getUserFirstName()+", "+user.getUserLastName());
        bookingConfirmation.setPitchName(pitch.getPitchName());
        bookingConfirmation.setDate(reservation.getDate());
        bookingConfirmation.setStartTime(reservation.getStartTime());
        bookingConfirmation.setEndTime(reservation.getEndTime());
        bookingConfirmation.setPitchAddress(pitch.getPitchAddress());

        try {
            emailServiceImplementation.sendMailConnfirmationBook(bookingConfirmation);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return modelMapper.map(savedReservation, ReservationResponseDTO.class);
    }

    @Override
    public ReservationResponseDTO getReservationById(Long id) {
        // Retrieve the reservation by its ID or throw ReservationNotFoundException
        Reservation reservation = iReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with ID " + id + " not found"));

        // Return response DTO with reservation details
        return modelMapper.map(reservation,ReservationResponseDTO.class);
    }

    @Override
    public List<ReservationResponseDTO> getAllReservations() {
        // Retrieve all reservations and map them to response DTOs
        return iReservationRepository.findAll().stream()
                .map(reservation -> modelMapper.map(reservation, ReservationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReservation(Long id) {
        // Check if the reservation exists before deleting it, throw exception if not found
        Reservation reservation = iReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with ID " + id + " not found"));

        iReservationRepository.delete(reservation);
    }

    @Override
    public List<ReservationResponseDTO> getReservationsByUserId(Long userId) {
        // Retrieve reservations by user ID and map them to response DTOs
        return iReservationRepository.findByUserId(userId).stream()
                .map(reservation -> modelMapper.map(reservation, ReservationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDTO> getReservationsByPitchId(Long pitchId) {
        // Retrieve reservations by pitch ID and map them to response DTOs
        return iReservationRepository.findByPitchId(pitchId).stream()
                .map(reservation -> modelMapper.map(reservation, ReservationResponseDTO.class))
                .collect(Collectors.toList());
    }

    private boolean checkAvailability(Long pitchId, LocalDate date, LocalTime startTime){
        return iReservationRepository.existsByPitchIdAndDateAndStartTime(pitchId, date, startTime);
    }
}