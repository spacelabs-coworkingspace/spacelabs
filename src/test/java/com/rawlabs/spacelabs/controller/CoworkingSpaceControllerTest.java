package com.rawlabs.spacelabs.controller;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import com.rawlabs.spacelabs.service.CoworkingSpaceService;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
@ContextConfiguration
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class CoworkingSpaceControllerTest {

    @MockBean
    private CoworkingSpaceService coworkingSpaceService;

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
    static class CoworkingSpaceControllerTestConfig{
       @Bean
        public CoworkingSpaceController coworkingSpaceController(CoworkingSpaceService coworkingSpaceService){
            return new CoworkingSpaceController(coworkingSpaceService);
        }
    }

    @Test
    void getCoworkingSpaces() throws Exception {
        when(coworkingSpaceService.getCoworkingSpaces(null, null)).thenReturn(
                List.of(CoworkingSpace.builder()
                        .id(1L)
                        .name("any name")
                        .address("any address")
                        .build())
        );
        mvc.perform(get("/coworking-space")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        when(coworkingSpaceService.getCoworkingSpaces(null, "any name")).thenReturn(
                List.of(CoworkingSpace.builder()
                        .id(1L)
                        .name("any name")
                        .address("any address")
                        .build())
        );
        mvc.perform(get("/coworking-space")
                .param("name", "any name")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        when(coworkingSpaceService.getCoworkingSpaces("any address", null)).thenReturn(
                List.of(CoworkingSpace.builder()
                        .id(1L)
                        .name("any name")
                        .address("any address")
                        .build())
        );
        mvc.perform(get("/coworking-space")
                .param("address", "any address")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        when(coworkingSpaceService.getCoworkingSpaces("any address", "any name")).thenReturn(
                List.of(CoworkingSpace.builder()
                        .id(1L)
                        .name("any name")
                        .address("any address")
                        .build())
        );
        mvc.perform(get("/coworking-space")
                .param("address", "any address")
                .param("name", "any name")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

    }

    @Test
    void getByID() throws Exception {
        when(coworkingSpaceService.getById(anyLong())).thenReturn(
                CoworkingSpace.builder()
                        .id(1L)
                        .name("any name")
                        .address("any address")
                        .build()
        );
        mvc.perform(get("/coworking-space/{id}", 1L)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("any name"));
    }

    @Test
    void deleteCoworkingSpace() throws Exception {

        doNothing().when(coworkingSpaceService).deleteById(anyLong());
        mvc.perform(delete("/coworking-space/{id}", 1L)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }
}