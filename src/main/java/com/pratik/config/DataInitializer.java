package com.pratik.config;

import com.pratik.entities.Role;
import com.pratik.entities.User;
import com.pratik.constant.enums.RoleType;
import com.pratik.repository.RoleRepository;
import com.pratik.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {

        return args -> {

            Role adminRole = roleRepository.findByName(RoleType.ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(null, RoleType.ADMIN)));

            Role managerRole = roleRepository.findByName(RoleType.MANAGER)
                    .orElseGet(() -> roleRepository.save(new Role(null, RoleType.MANAGER)));

            Role userRole = roleRepository.findByName(RoleType.USER)
                    .orElseGet(() -> roleRepository.save(new Role(null, RoleType.USER)));

            if (!userRepository.existsByEmail("admin@gmail.com")) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Set.of(adminRole));

                userRepository.save(admin);
            }
        };
    }
}