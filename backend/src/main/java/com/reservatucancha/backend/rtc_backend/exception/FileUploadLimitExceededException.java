package com.reservatucancha.backend.rtc_backend.exception;

public class FileUploadLimitExceededException extends RuntimeException{
    public FileUploadLimitExceededException(String message) {
        super(message);
    }
}
