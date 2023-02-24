package com.rawlabs.spacelabs.controller;

import com.rawlabs.spacelabs.domain.dto.LoginResponseDto;
import com.rawlabs.spacelabs.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ContextConfiguration
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Configuration
    @EnableWebMvc
    static class AuthControllerTestConfig {
        @Bean
        public AuthController authController(AuthService authService) {
            return new AuthController(authService);
        }
    }

    @Test
    void login_Test() throws Exception {
        when(authService.doLogin(any())).thenReturn(LoginResponseDto.builder()
                        .accessToken("anyToken")
                        .expiresIn(LocalDateTime.now())
                .build());
        mvc.perform(post("/auth/login")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.accessToken").value("anyToken"))
                .andExpect(jsonPath("$.expiresIn").isNotEmpty());
    }

}
