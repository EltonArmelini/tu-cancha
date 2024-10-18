package com.reservatucancha.backend.rtc_backend.controller;


import com.reservatucancha.backend.rtc_backend.dto.UserEditDTO;
import com.reservatucancha.backend.rtc_backend.dto.request.AuthLoginRequest;
import com.reservatucancha.backend.rtc_backend.dto.request.UserRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.UserResponseDTO;
import com.reservatucancha.backend.rtc_backend.service.implementation.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Gestión del perfil del Usuario")
public class UserProfileController {

    @Autowired
    private UserService userService;

    //Obtener la info del usuario
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUserProfileInformation(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.getUserInfo(userRequestDTO));
    }


    //Modificar la info del usuario(perfil)
    @PutMapping("/me")
    public ResponseEntity<UserEditDTO> putUserProfileInformation(@RequestBody @Valid  UserEditDTO userEditDTO) {
        return ResponseEntity.ok(userService.putUserInfo(userEditDTO));
    }


    //Modificar la contraseña del usario
    @PatchMapping("/me/userPassword")
    public ResponseEntity<UserResponseDTO> putUserPassword(@RequestBody AuthLoginRequest authLoginRequest) {
        return ResponseEntity.ok(userService.putUserPassword(authLoginRequest));
    }

    //destruir la cuenta del usario
        @DeleteMapping("/me/destroy")
    public ResponseEntity<String> deleteUser(@RequestBody AuthLoginRequest authLoginRequest) {
        return new ResponseEntity<>(userService.deleteUser(authLoginRequest), HttpStatus.NO_CONTENT);
    }
}
