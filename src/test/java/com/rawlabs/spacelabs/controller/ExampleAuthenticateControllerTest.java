package com.rawlabs.spacelabs.controller;

import com.rawlabs.spacelabs.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@ContextConfiguration
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class ExampleAuthenticateControllerTest {

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
    static class ExampleAuthenticateControllerTestConfig {
        @Bean
        public ExampleAuthenticateController exampleAuthenticateController() {
            return new ExampleAuthenticateController();
        }
    }

    @Test
    void example_Test() throws Exception {
        mvc.perform(get("/example")
                        .content("{}")
                        .principal(() -> "any")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value(ErrorCode.SUCCESS.name()))
                .andExpect(jsonPath("$.description").isNotEmpty())
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

}