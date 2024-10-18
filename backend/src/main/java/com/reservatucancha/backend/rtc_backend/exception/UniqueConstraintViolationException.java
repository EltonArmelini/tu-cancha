package com.reservatucancha.backend.rtc_backend.exception;

public class UniqueConstraintViolationException extends RuntimeException{

    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}
