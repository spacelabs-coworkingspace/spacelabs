package com.rawlabs.spacelabs.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rawlabs.spacelabs.constant.ErrorCode;
import com.rawlabs.spacelabs.domain.dao.User;
import com.rawlabs.spacelabs.domain.dto.RegisterRequestDto;
import com.rawlabs.spacelabs.domain.dto.RegisterResponseDto;
import com.rawlabs.spacelabs.exception.SpaceLabsException;
import com.rawlabs.spacelabs.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return user;
    }

    public UserDetails getProfile(Principal principal){

        log.info("get name from profile : " , principal.getName());
        String username = principal.getName();

        User user = userRepository.findUserByUsername(username);
        log.info("Get user_id and name from username ", user.getId(),user.getFullName());

        return user;
    }

    public RegisterResponseDto doRegister(RegisterRequestDto request) {
        log.info("Begin do Regiter with request :: {}", request);
        User user;
        try {
            user = userRepository.save(User.builder()
                    .createdDate(LocalDateTime.now())
                    .isDeleted(Boolean.FALSE)
                    .username(request.getEmail())
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build()
            );
            log.info("Register response ::", user.getUsername(), user.getEmail());


        } catch (Exception e) {
            log.error("Error Register {}", e);
            throw new SpaceLabsException("Error Register", ErrorCode.UNKNOWN_ERROR.name());
        }


        return RegisterResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

    }

}
