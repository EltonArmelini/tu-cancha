package com.reservatucancha.backend.rtc_backend.controller;


import com.reservatucancha.backend.rtc_backend.dto.UserEditDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.UserResponseDTO;
import com.reservatucancha.backend.rtc_backend.entity.User.UserEntity;
import com.reservatucancha.backend.rtc_backend.service.implementation.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
@Tag(name = "Administración", description = "Endpoints para la gestión de usuarios por parte de administradores")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("get/user")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUser());
    }
    @GetMapping("get/user/{id}")
    public ResponseEntity<UserResponseDTO> getAllUsers(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("edit/user/doadmin/{mail}")
    public ResponseEntity<UserEditDTO> doAdmin(@PathVariable String mail){
        return ResponseEntity.ok(userService.putUserInfo(mail));
    }

}
