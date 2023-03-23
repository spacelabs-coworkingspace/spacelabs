package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.User;
import com.rawlabs.spacelabs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    void loadUserByUsername_Success_Test() {
        when(userRepository.findUserByUsername(any())).thenReturn(User.builder()
                        .id(1L)
                        .username("any")
                        .password("password")
                .build());
        when(passwordEncoder.encode(any())).thenReturn("Encrypt password");
        User user = (User) userService.loadUserByUsername("any");
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("any", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void loadUserByUsername_Exception_Test() {
        when(userRepository.findUserByUsername(any())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("any"));
    }

    @Test
    void doRegister_Success_Test(){

        when(userRepository.save(any())).thenReturn(User.builder()
                .createdDate(LocalDateTime.now())
                .isDeleted(Boolean.FALSE)
                .username("any name")
                .fullName(any())
                .email("any email")
                .password("password")
                .build());
        when(passwordEncoder.encode("password")).thenReturn("Encrypt password");

    }

}