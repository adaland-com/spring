package com.adaland.springsecurity.config;


import com.adaland.springsecurity.model.auth.Role;
import com.adaland.springsecurity.model.auth.User;
import com.adaland.springsecurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
// TODO (Ada)
@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {


    private final AuthenticationService authenticationService;

    @EventListener(ApplicationReadyEvent.class)
    void setUp() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(admin.getUsername());
        admin.setRole(Role.ADMIN);
//        authenticationService.register(admin);

    }

}
