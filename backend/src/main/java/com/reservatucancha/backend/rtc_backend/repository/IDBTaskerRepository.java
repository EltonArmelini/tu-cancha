package com.reservatucancha.backend.rtc_backend.repository;

import com.reservatucancha.backend.rtc_backend.entity.DBTasker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDBTaskerRepository extends JpaRepository<DBTasker,Long> {
    Optional<DBTasker> findByScriptName(String scriptName);
}
