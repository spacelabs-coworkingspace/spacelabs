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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void loadUserByUsername_Success_Test() {
        when(userRepository.findUserByUsername(any())).thenReturn(User.builder()
                        .id(1L)
                        .username("any")
                        .password("password")
                .build());
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

}