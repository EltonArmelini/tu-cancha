package com.reservatucancha.backend.rtc_backend.service;

import com.reservatucancha.backend.rtc_backend.dto.UserEditDTO;
import com.reservatucancha.backend.rtc_backend.dto.request.AuthLoginRequest;
import com.reservatucancha.backend.rtc_backend.dto.request.UserRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.UserResponseDTO;
import com.reservatucancha.backend.rtc_backend.entity.User.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserResponseDTO> getAllUser();

    UserResponseDTO getUserInfo(UserRequestDTO userRequestDTO);

    UserEditDTO putUserInfo(UserEditDTO userEditDTO);

    UserEditDTO putUserInfo(String mail);

    UserResponseDTO putUserPassword(AuthLoginRequest authLoginRequest);

    String deleteUser(AuthLoginRequest authLoginRequest);

    UserResponseDTO getUserById(Long userId);
}

