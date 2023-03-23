package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.User;
import com.rawlabs.spacelabs.domain.dto.RegisterRequestDto;
import com.rawlabs.spacelabs.domain.dto.RegisterResponseDto;
import com.rawlabs.spacelabs.mock.PrincipalMock;
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
        when(passwordEncoder.encode(any())).thenReturn("Encrpt password");
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
    void getProfile_Success_Test(){

        when(userRepository.findUserByUsername(any())).thenReturn(
                User.builder()
                        .id(1L)
                        .username("anyusername")
                        .fullName("John Doe")
                        .build()
        );

        User user = userService.getProfile(new PrincipalMock());
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("anyusername", user.getUsername());

    }

    @Test
    void doRegister_Success_Test(){
        when(userRepository.save(any())).thenReturn(
                User.builder()
                        .id(1L)
                        .username("username")
                        .build()
        );

        when(passwordEncoder.encode(any())).thenReturn("password encrypt");

        RegisterResponseDto response = userService.doRegister(RegisterRequestDto.builder()
                        .fullName("john doe")
                        .email("anymail")
                        .password("password")
                .build());

        assertNotNull(response);
        assertEquals("username", response.getUsername());

    }

    @Test
    void doRegister_Failed_Test(){
        when(userRepository.save(any())).thenThrow(
              new NullPointerException()
        );

        when(passwordEncoder.encode(any())).thenReturn("password encrypt");
        assertThrows(Exception.class, ()->userService.doRegister(RegisterRequestDto.builder()
                .fullName("john doe")
                .email("anymail")
                .password("password")
                .build()));

    }



}