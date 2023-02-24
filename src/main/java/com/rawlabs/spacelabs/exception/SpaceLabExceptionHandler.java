package com.rawlabs.spacelabs.exception;

import com.rawlabs.spacelabs.constant.ErrorCode;
import com.rawlabs.spacelabs.domain.dto.GeneralMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class SpaceLabExceptionHandler {

    @ExceptionHandler(SpaceLabsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralMessageDto spaceLabsException(SpaceLabsException e) {
        return GeneralMessageDto.builder()
                .code(e.getCode())
                .description(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralMessageDto unknownException(Exception e) {
        return GeneralMessageDto.builder()
                .code(ErrorCode.UNKNOWN_ERROR.name())
                .description(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({UsernameNotFoundException.class, UnAuthorizedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralMessageDto unauthorizedException(UsernameNotFoundException e) {
        return GeneralMessageDto.builder()
                .code(ErrorCode.UNAUTHORIZED.name())
                .description(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
