package com.rawlabs.spacelabs.controller;

import com.rawlabs.spacelabs.constant.ErrorCode;
import com.rawlabs.spacelabs.domain.dto.GeneralMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/example")
@SecurityRequirement(name = "bearer")
public class ExampleAuthenticateController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Example authenticated endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public GeneralMessageDto example(Principal principal) {
        log.info("How to get username from spring security");
        log.info("Use [Principal] at method parameter -> principal.getName()");
        log.info("Username :: {}", principal.getName());
        return GeneralMessageDto.builder()
                .code(ErrorCode.SUCCESS.name())
                .description("Sukses")
                .timestamp(LocalDateTime.now())
                .build();
    }

}
