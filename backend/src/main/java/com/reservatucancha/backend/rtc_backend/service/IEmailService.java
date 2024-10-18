package com.reservatucancha.backend.rtc_backend.service;

import com.reservatucancha.backend.rtc_backend.dto.response.BookingConfirmation;
import com.reservatucancha.backend.rtc_backend.dto.response.ReservationResponseDTO;
import jakarta.mail.MessagingException;

public interface IEmailService {

    void sendMailConnfirmationBook(BookingConfirmation createdReservation) throws MessagingException;
}
