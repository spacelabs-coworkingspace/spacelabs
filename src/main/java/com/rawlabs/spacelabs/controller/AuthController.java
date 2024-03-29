package com.rawlabs.spacelabs.controller;

import com.rawlabs.spacelabs.domain.dto.LoginRequestDto;
import com.rawlabs.spacelabs.domain.dto.LoginResponseDto;
import com.rawlabs.spacelabs.domain.dto.RegisterRequestDto;
import com.rawlabs.spacelabs.domain.dto.RegisterResponseDto;
import com.rawlabs.spacelabs.service.AuthService;
import com.rawlabs.spacelabs.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {
        return authService.doLogin(request);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "register")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    public RegisterResponseDto register(@RequestBody RegisterRequestDto request){
        return userService.doRegister(request);
    }

}
