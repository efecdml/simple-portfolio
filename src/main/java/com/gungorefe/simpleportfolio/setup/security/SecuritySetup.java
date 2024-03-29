package com.gungorefe.simpleportfolio.setup.security;

import com.gungorefe.simpleportfolio.dto.security.RoleName;
import com.gungorefe.simpleportfolio.entity.security.Role;
import com.gungorefe.simpleportfolio.entity.security.User;
import com.gungorefe.simpleportfolio.repository.security.RoleRepository;
import com.gungorefe.simpleportfolio.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Log4j2
@Component
public class SecuritySetup implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private Role roleAdmin;
    private Role roleMod;
    private final String adminEmail = "admin@email.com";
    private final String adminPassword = "admin";
    private final String modEmail = "mod@email.com";
    private final String modPassword = "mod";

    private void insertRolesIfNotExist() {
        if (!roleRepository.existsByName(RoleName.ROLE_ADMIN)) {
            log.warn("Administrator role not found, inserting..");
            roleAdmin = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        }
        if (!roleRepository.existsByName(RoleName.ROLE_MOD)) {
            log.warn("Moderator role not found, inserting..");
            roleMod = roleRepository.save(new Role(RoleName.ROLE_MOD));
        }
    }

    private void insertUsersIfNotExist() {
        if (!userRepository.existsByRole_Name(RoleName.ROLE_ADMIN)) {
            log.warn("No administrator users found, inserting..");
            userRepository.save(new User(
                    adminEmail,
                    passwordEncoder.encode(adminPassword),
                    roleAdmin
            ));
        }
    }

    private void initTestSetup() {
        if (!userRepository.existsByRole_Name(RoleName.ROLE_MOD)) {
            log.info("Inserting a moderator user for testing purpose..");
            userRepository.save(new User(
                    modEmail,
                    passwordEncoder.encode(modPassword),
                    roleMod
            ));
        }

        log.info(MessageFormat.format(
                "Administrator user email: {0}, password: {1}",
                adminEmail,
                adminPassword
        ));
        log.info(MessageFormat.format(
                "Moderator user email: {0}, password: {1}",
                modEmail,
                modPassword
        ));
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        insertRolesIfNotExist();
        insertUsersIfNotExist();
        initTestSetup();
    }
}
