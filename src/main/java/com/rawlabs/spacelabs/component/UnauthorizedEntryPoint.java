package com.rawlabs.spacelabs.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rawlabs.spacelabs.constant.ErrorCode;
import com.rawlabs.spacelabs.domain.dto.GeneralMessageDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    public static final String X_ERROR_MESSAGE = "X-Error-Message";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        PrintWriter writer = response.getWriter();

        String errorMessage = response.getHeader(X_ERROR_MESSAGE);
        if (errorMessage == null) errorMessage = authException.getMessage();

        writer.write(mapper.writeValueAsString(GeneralMessageDto.builder()
                .code(ErrorCode.UNAUTHORIZED.name())
                .description(errorMessage)
                .timestamp(LocalDateTime.now())
                .build()));
        writer.flush();
        writer.close();
    }

}
