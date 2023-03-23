package com.rawlabs.spacelabs.mock;

import javax.security.auth.Subject;
import java.security.Principal;

public class PrincipalMock implements Principal {
    @Override
    public String getName() {
        return "username";
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
