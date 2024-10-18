package com.reservatucancha.backend.rtc_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthCreateUserRequest {
    @NotBlank
    String userEmail;
    @NotBlank
    String userFirstName;
    @NotBlank
    String userLastName;
    @NotBlank
    String userPassword;
}


