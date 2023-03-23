package com.rawlabs.spacelabs.controller;

import com.rawlabs.spacelabs.domain.dao.Transaction;
import com.rawlabs.spacelabs.domain.dto.TransactionExecuteDto;
import com.rawlabs.spacelabs.domain.dto.TransactionInquiryDto;
import com.rawlabs.spacelabs.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/book")
@SecurityRequirement(name = "bearer")
public class BookingController {
    private final TransactionService transactionService;

    public BookingController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/inquiry", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "inquiry booking")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    public Transaction inquiry(@RequestBody TransactionInquiryDto request){
        return transactionService.inquiry(request);
    }

    @PostMapping(value = "/execute", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Execute transaction tobe PAID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    public Transaction execute(@RequestBody TransactionExecuteDto request){
        return transactionService.execute(request);
    }

}
