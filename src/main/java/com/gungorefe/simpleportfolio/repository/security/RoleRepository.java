package com.gungorefe.simpleportfolio.repository.security;

import com.gungorefe.simpleportfolio.dto.security.RoleName;
import com.gungorefe.simpleportfolio.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByName(RoleName name);

    Optional<Role> findByName(RoleName name);
}
