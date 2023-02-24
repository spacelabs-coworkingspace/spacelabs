package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.component.TokenProvider;
import com.rawlabs.spacelabs.domain.dao.User;
import com.rawlabs.spacelabs.domain.dto.LoginRequestDto;
import com.rawlabs.spacelabs.domain.dto.LoginResponseDto;
import com.rawlabs.spacelabs.exception.UnAuthorizedException;
import com.rawlabs.spacelabs.mock.AuthenticationMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthService.class)
class AuthServiceTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @Test
    void doLogin_Success_Test() {
        LocalDateTime today = LocalDateTime.now();
        when(userService.loadUserByUsername(any())).thenReturn(User.builder()
                        .id(1L)
                        .username("any")
                        .password("password")
                .build());
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(new AuthenticationMock());
        when(tokenProvider.generateToken(any())).thenReturn("anyToken");
        when(tokenProvider.getExpirationDate(any())).thenReturn(today);

        LoginResponseDto response = authService.doLogin(LoginRequestDto.builder().build());
        assertNotNull(response);
        assertEquals("anyToken", response.getAccessToken());
        assertEquals(today, response.getExpiresIn());
    }

    @Test
    void doLogin_UnauthorizedException_Test() {
        when(userService.loadUserByUsername(any())).thenReturn(User.builder()
                .id(1L)
                .username("any")
                .password("password")
                .build());
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.doLogin(LoginRequestDto.builder().build()));
    }

}