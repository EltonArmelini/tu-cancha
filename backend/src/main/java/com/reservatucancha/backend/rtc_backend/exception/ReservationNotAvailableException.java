package com.reservatucancha.backend.rtc_backend.exception;

public class ReservationNotAvailableException extends RuntimeException{
    public ReservationNotAvailableException(String message) {
        super(message);
    }
}
