package com.rawlabs.spacelabs.controller;

import com.rawlabs.spacelabs.domain.dao.User;
import com.rawlabs.spacelabs.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ContextConfiguration
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class ProfileControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Configuration
    @EnableWebMvc
    static class ProfileControllerTestConfig {
        @Bean
        public ProfileController profileController(UserService userService){
            return new ProfileController(userService);
        }

    }

    @Test
    void getProfile_Success() throws Exception {
        when(userService.getProfile(any())).thenReturn(User.builder()
                .id(1L)
                .fullName("john doe")
                .email("any email")
                .build());
        mvc.perform(get("/profile")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L));
    }

}