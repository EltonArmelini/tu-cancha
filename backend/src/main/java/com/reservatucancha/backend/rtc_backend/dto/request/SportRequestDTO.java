package com.reservatucancha.backend.rtc_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SportRequestDTO {

    @NotNull(message = "The sport name can't be null")
    @NotBlank(message = "The sport name must be specified")
    private String sport;

}
