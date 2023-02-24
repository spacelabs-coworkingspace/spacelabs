package com.rawlabs.spacelabs.component;

import com.rawlabs.spacelabs.domain.dao.User;
import com.rawlabs.spacelabs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class OnApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String username = "user";
        String defaultPassword = passwordEncoder.encode("password");
        log.debug("Default user -> [username: {}] [password: {}", username, defaultPassword);
        userRepository.save(User.builder()
                        .createdDate(LocalDateTime.now())
                        .isDeleted(Boolean.FALSE)
                        .fullName("Initial User Should be Removed")
                        .username(username)
                        .email("spacelabs@mail.local")
                        .password(defaultPassword)
                .build());
    }
}
