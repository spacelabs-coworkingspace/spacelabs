package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.component.TokenProvider;
import com.rawlabs.spacelabs.domain.dao.User;
import com.rawlabs.spacelabs.domain.dto.LoginRequestDto;
import com.rawlabs.spacelabs.domain.dto.LoginResponseDto;
import com.rawlabs.spacelabs.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserService userService,
                       TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDto doLogin(LoginRequestDto request) {
        log.info("Begin do login with request :: {}", request);
        User user = (User) userService.loadUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnAuthorizedException("Invalid username or password!");
        }

        log.info("Create authentication context");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);

        return LoginResponseDto.builder()
                .accessToken(token)
                .expiresIn(tokenProvider.getExpirationDate(token))
                .build();
    }

}
