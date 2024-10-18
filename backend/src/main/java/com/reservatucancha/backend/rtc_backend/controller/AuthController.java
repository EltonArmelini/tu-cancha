package com.reservatucancha.backend.rtc_backend.controller;


import com.reservatucancha.backend.rtc_backend.dto.request.AuthCreateUserRequest;
import com.reservatucancha.backend.rtc_backend.dto.request.AuthLoginRequest;
import com.reservatucancha.backend.rtc_backend.dto.response.AuthLoginResponse;
import com.reservatucancha.backend.rtc_backend.service.implementation.UserDetailServiceImplementation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para el registro y login de usuarios")
public class AuthController {

    private UserDetailServiceImplementation userDetailService;

    @PostMapping("/signup")
    public ResponseEntity<AuthLoginResponse> register(@Valid @RequestBody AuthCreateUserRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> login(@Valid @RequestBody AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

}
