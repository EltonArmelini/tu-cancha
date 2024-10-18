package com.reservatucancha.backend.rtc_backend.service.implementation;


import com.reservatucancha.backend.rtc_backend.dto.UserEditDTO;
import com.reservatucancha.backend.rtc_backend.dto.request.AuthLoginRequest;
import com.reservatucancha.backend.rtc_backend.dto.request.UserRequestDTO;
import com.reservatucancha.backend.rtc_backend.dto.response.UserResponseDTO;
import com.reservatucancha.backend.rtc_backend.entity.User.RoleEnum;
import com.reservatucancha.backend.rtc_backend.entity.User.Role;
import com.reservatucancha.backend.rtc_backend.entity.User.UserEntity;
import com.reservatucancha.backend.rtc_backend.exception.PasswordSameAsOldException;
import com.reservatucancha.backend.rtc_backend.exception.ResourceNotFoundException;
import com.reservatucancha.backend.rtc_backend.repository.IUserRepository;
import com.reservatucancha.backend.rtc_backend.repository.IRoleRepository;
import com.reservatucancha.backend.rtc_backend.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {


    private final IUserRepository IUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository IRoleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(IUserRepository IUserRepository, PasswordEncoder passwordEncoder, IRoleRepository IRoleRepository, ModelMapper modelMapper) {
        this.IUserRepository = IUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.IRoleRepository = IRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserResponseDTO> getAllUser() {
        List<UserEntity> users = IUserRepository.findAll();
        List<UserResponseDTO> usersResponse = new ArrayList<>();

        users.forEach(user -> {
            UserResponseDTO dto = new UserResponseDTO(user.getUserEmail(), user.getUserFirstName(), user.getUserLastName(),user.getRoles().stream()
                    .map(role -> role.getRoleEnum().name())
                    .collect(Collectors.joining(", ")));
            usersResponse.add(dto);
        });

        return usersResponse;
    }



    @Override
    public UserResponseDTO getUserInfo(UserRequestDTO userRequestDTO) {
        UserEntity user = IUserRepository.findUserByUserEmail(userRequestDTO.email()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new UserResponseDTO(user.getUserEmail(), user.getUserFirstName(), user.getUserLastName(), user.getRoles().stream()
                .map(role -> role.getRoleEnum().name())
                .collect(Collectors.joining(", ")));
    }

    @Override
    public UserEditDTO putUserInfo(UserEditDTO userEditDTO) {
        //Busco el user con el mail que me manda, si lo encuentra sigo... sino tiro excepcion
        UserEntity user = IUserRepository.findUserByUserEmail(userEditDTO.getUserEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //seteo la data que me llego al usuario que me llego
        user.setUserEmail(userEditDTO.getUserEmail());
        user.setUserFirstName(userEditDTO.getUserFirstName());
        user.setUserLastName(userEditDTO.getUserLastName());
        IUserRepository.save(user);

        //devuelvo la data al controller
        return userEditDTO;
    }

    @Override
    public UserEditDTO putUserInfo(String mail) {
        //Busco el user con el mail que me manda, si lo encuentra sigo... sino tiro excepcion
        UserEntity user = IUserRepository.findUserByUserEmail(mail).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // Busco el rol ADMIN en la base de datos
        Role adminRole = IRoleRepository.findByRoleEnum(RoleEnum.OPERATOR).orElseThrow(() -> new ResourceNotFoundException("Role not fount"));
        // Asigno el rol ADMIN al usuario
        user.setRoles(new HashSet<>(List.of(adminRole)));
        UserEntity saved = IUserRepository.save(user);
        //devuelvo la data al controller
        return modelMapper.map(user, UserEditDTO.class);
    }

    @Override
    public UserResponseDTO putUserPassword(AuthLoginRequest authLoginRequest) {
        //Busco el mail que me llego, para revisar que el usario si exiteste... sino lanzo excepcion
        UserEntity user = IUserRepository.findUserByUserEmail(authLoginRequest.userEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //Verifico que la contraseña que me quiere dar, no es la misma. Si es la misma lanzo excepcion
        if (passwordEncoder.matches(authLoginRequest.userPassword(), user.getPassword())) {
            throw new PasswordSameAsOldException("You can't use the same userPassword, please change the userPassword");
        }

        //Seteo la nueva contraseña y la guardo en la base de datos
        user.setPassword(passwordEncoder.encode(authLoginRequest.userPassword()));
        IUserRepository.save(user);

        //retorno toda la data, por buena practica
        return new UserResponseDTO(user.getUserEmail(), user.getUserFirstName(), user.getUserLastName(), user.getRoles().toString());
    }

    @Override
    public String deleteUser(AuthLoginRequest authLoginRequest) {
        UserEntity user = IUserRepository.findUserByUserEmail(authLoginRequest.userEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        IUserRepository.deleteById(user.getId());
        return "user destroyed";
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        UserEntity user = IUserRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User with id: "+id+" not found"));
        return new UserResponseDTO(user.getUserEmail(), user.getUserFirstName(),user.getUserFirstName(),user.getRoles().stream()
                .map(role -> role.getRoleEnum().name())
                .collect(Collectors.joining(", ")));
    }
}
