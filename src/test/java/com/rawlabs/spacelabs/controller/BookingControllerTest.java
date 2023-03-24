package com.rawlabs.spacelabs.controller;

import com.rawlabs.spacelabs.domain.dao.Transaction;
import com.rawlabs.spacelabs.service.TransactionService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ContextConfiguration
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class BookingControllerTest {

    @MockBean
    private TransactionService transactionService;
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
    static class BookingControllerTestConfig{
        @Bean
        public BookingController bookingController(TransactionService transactionService){
            return new BookingController(transactionService);
        }
    }

    @Test
    void inquiry_Test() throws Exception {
        when(transactionService.inquiry(any())).thenReturn(
                Transaction.builder()
                        .transactionId(1L)
                        .status("PENDING")
                        .build()
        );
        mvc.perform(post("/book/inquiry")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void execute_Test() throws Exception {
        when(transactionService.execute(any())).thenReturn(
                Transaction.builder()
                        .transactionId(1L)
                        .status("PAID")
                        .build()
        );
        mvc.perform(post("/book/execute")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.status").value("PAID"));
    }

}