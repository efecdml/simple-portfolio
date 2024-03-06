package com.gungorefe.simpleportfolio.repository.security;

import com.gungorefe.simpleportfolio.dto.security.RoleName;
import com.gungorefe.simpleportfolio.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByRole_Name(RoleName roleName);

    @Query("""
            SELECT u
            FROM User u
            LEFT JOIN FETCH u.role
            WHERE u.email = ?1
            """)
    Optional<User> findWithRoleByEmail(String email);
}
