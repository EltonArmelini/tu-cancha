package com.reservatucancha.backend.rtc_backend.repository;


import com.reservatucancha.backend.rtc_backend.entity.User.Role;
import com.reservatucancha.backend.rtc_backend.entity.User.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Set;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    Set<Role> findRoleEntitiesByRoleEnumIn(Set<RoleEnum> roles);
    Optional<Role> findByRoleEnum(RoleEnum roleEnum);
}
