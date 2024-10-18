package com.reservatucancha.backend.rtc_backend.exception;

public class PasswordSameAsOldException extends RuntimeException{

    public PasswordSameAsOldException(String message) {
        super(message);
    }

}
