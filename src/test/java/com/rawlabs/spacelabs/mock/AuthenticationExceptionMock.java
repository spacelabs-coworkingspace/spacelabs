package com.rawlabs.spacelabs.mock;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationExceptionMock extends AuthenticationException {
    public AuthenticationExceptionMock(String msg) {
        super(msg);
    }
}
