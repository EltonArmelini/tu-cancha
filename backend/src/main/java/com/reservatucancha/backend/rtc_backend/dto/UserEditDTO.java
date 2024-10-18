package com.reservatucancha.backend.rtc_backend.dto;

import com.reservatucancha.backend.rtc_backend.entity.User.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Data
public class UserEditDTO{
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private Set<Role> roles;
}
