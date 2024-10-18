package com.reservatucancha.backend.rtc_backend.exception;

public class InvalidSearchParameterException extends RuntimeException{
    public InvalidSearchParameterException(String message) {
        super(message);
    }
}
