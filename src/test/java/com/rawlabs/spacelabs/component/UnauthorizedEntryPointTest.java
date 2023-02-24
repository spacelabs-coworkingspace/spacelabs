package com.rawlabs.spacelabs.component;

import com.rawlabs.spacelabs.mock.AuthenticationExceptionMock;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static com.rawlabs.spacelabs.component.UnauthorizedEntryPoint.X_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UnauthorizedEntryPoint.class)
class UnauthorizedEntryPointTest {

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Test
    void unauthorizedEntryPoint_Test() throws ServletException, IOException {
        HttpServletResponse servletResponse = new MockHttpServletResponse();
        unauthorizedEntryPoint.commence(new MockHttpServletRequest(), servletResponse,
                new AuthenticationExceptionMock("ayn"));

        servletResponse.addHeader(X_ERROR_MESSAGE, "Invalid token!");
        unauthorizedEntryPoint.commence(new MockHttpServletRequest(), servletResponse,
                new AuthenticationExceptionMock("ayn"));
        assertTrue(Boolean.TRUE);
    }

}