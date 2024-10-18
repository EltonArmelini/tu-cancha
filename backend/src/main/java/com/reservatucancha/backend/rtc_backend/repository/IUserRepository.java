package com.reservatucancha.backend.rtc_backend.repository;

import com.reservatucancha.backend.rtc_backend.entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface
IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByUserEmail(String email);
}

