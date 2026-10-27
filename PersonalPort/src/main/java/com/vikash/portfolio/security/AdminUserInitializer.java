package com.vikash.portfolio.security;

import com.vikash.portfolio.entity.AdminUser;
import com.vikash.portfolio.repository.AdminUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminUserInitializer.class);

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:Admin@Vikash2026!}")
    private String adminPassword;

    @Value("${app.admin.email:admin@vikashpandey.dev}")
    private String adminEmail;

    public AdminUserInitializer(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!adminUserRepository.existsByUsername(adminUsername)) {
            log.info("No admin user found with username '{}'. Initializing default administrator account.", adminUsername);
            AdminUser admin = new AdminUser(
                    adminUsername,
                    passwordEncoder.encode(adminPassword),
                    adminEmail,
                    "ROLE_ADMIN"
            );
            adminUserRepository.save(admin);
            log.info("Default administrator account initialized successfully.");
        } else {
            log.info("Administrator user '{}' is already configured.", adminUsername);
        }
    }
}
