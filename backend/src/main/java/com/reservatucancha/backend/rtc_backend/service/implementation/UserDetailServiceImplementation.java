package com.reservatucancha.backend.rtc_backend.service.implementation;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.reservatucancha.backend.rtc_backend.dto.request.AuthCreateUserRequest;
import com.reservatucancha.backend.rtc_backend.dto.request.AuthLoginRequest;
import com.reservatucancha.backend.rtc_backend.dto.response.AuthLoginResponse;
import com.reservatucancha.backend.rtc_backend.entity.User.Role;
import com.reservatucancha.backend.rtc_backend.entity.User.RoleEnum;
import com.reservatucancha.backend.rtc_backend.entity.User.UserEntity;
import com.reservatucancha.backend.rtc_backend.exception.UniqueConstraintViolationException;
import com.reservatucancha.backend.rtc_backend.repository.IRoleRepository;
import com.reservatucancha.backend.rtc_backend.repository.IUserRepository;
import com.reservatucancha.backend.rtc_backend.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor @NoArgsConstructor
public class UserDetailServiceImplementation implements UserDetailsService {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository IUserRepository;

    @Autowired
    private IRoleRepository IRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = IUserRepository.findUserByUserEmail(email).orElseThrow(() -> {
            LOGGER.info("--- Login error, user not found -----");
            return new UsernameNotFoundException("User with "+ email +" not found");
        });

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        user.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        return new User(user.getUserEmail(),
                        user.getPassword(),
                        user.isEnabled(),
                        user.isAccountNoExpired(),
                        user.isCredentialNoExpired(),
                        user.isAccountNoLocked(),
                        authorityList
                        );
    }
    public AuthLoginResponse loginUser(AuthLoginRequest loginRequest){
        String email = loginRequest.userEmail();
        String password = loginRequest.userPassword();

        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateToken(authentication);
        DecodedJWT decodedJWT = jwtUtils.jwtValidation(accessToken);
        String expirationDate = jwtUtils.getExpirationDate(decodedJWT).toString();

        String roles = IUserRepository.findUserByUserEmail(email).get().getRoles().stream()
                .map(role -> role.getRoleEnum().name())
                .collect(Collectors.joining(", "));
        UserEntity userInfo =  IUserRepository.findUserByUserEmail(email).get();
        Long userId = userInfo.getId();
        return new AuthLoginResponse(userId,email,userInfo.getUserFirstName(),userInfo.getUserFirstName(),roles,"User logged successfully",expirationDate, accessToken, true);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = loadUserByUsername(email);


        if (userDetails == null|| !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or userPassword");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    public AuthLoginResponse createUser(AuthCreateUserRequest userRequest) {
        if (checkEmailExist(userRequest.getUserEmail())){
            throw new UniqueConstraintViolationException("Email provided: "+userRequest.getUserEmail()+" already exists");
        }
        String email = userRequest.getUserEmail();
        String firstName = userRequest.getUserFirstName();
        String lastName = userRequest.getUserLastName();
        String password = userRequest.getUserPassword();
        Set<RoleEnum> rolesRequest = Set.of(RoleEnum.CLIENT);

        System.out.println("Roles: "+ IRoleRepository.findAll());

        Set<Role> roles = IRoleRepository.findRoleEntitiesByRoleEnumIn(rolesRequest);

        if (roles.isEmpty()) {
            throw new IllegalArgumentException("The roles specified do not exist.");
        }


        UserEntity userEntity = UserEntity.builder()
                .userEmail(email)
                .userFirstName(firstName)
                .userLastName(lastName)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();

        UserEntity userSaved = IUserRepository.save(userEntity);
        Long id= userSaved.getId();

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userSaved.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtils.generateToken(authentication);
        String userFirstName = userSaved.getUserFirstName();
        String userLastName=userSaved.getUserLastName();
        DecodedJWT decodedJWT = jwtUtils.jwtValidation(accessToken);
        String expirationDate = jwtUtils.getExpirationDate(decodedJWT).toString();

        return new AuthLoginResponse(id,
                                    email,
                                    userFirstName,
                                    userLastName,
                                    roles.stream()
                                            .map(role -> role.getRoleEnum().name())
                                           .collect(Collectors.joining(","))
                                    ,"User created successfully",
                                    expirationDate,
                                    accessToken,
                                    true);
    }

    private boolean checkEmailExist(String email){
        return IUserRepository.findUserByUserEmail(email).isPresent();
    }
}
