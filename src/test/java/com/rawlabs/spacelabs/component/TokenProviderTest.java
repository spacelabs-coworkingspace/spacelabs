package com.rawlabs.spacelabs.component;

import com.rawlabs.spacelabs.domain.dao.User;
import com.rawlabs.spacelabs.mock.AuthenticationMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TokenProvider.class)
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    private String accessToken;
    private final Authentication authentication = new AuthenticationMock();

    @Value("${jwt.signing.key}")
    private String jwtSigningKey;

    @BeforeEach
    void setup() {
        this.accessToken = tokenProvider.generateToken(authentication);
    }

    @Test
    void tokenProvider_Test() {
        User user = User.builder()
                .username(authentication.getName())
                .build();
        User user2 = User.builder()
                .username("anyName")
                .build();

        assertEquals(authentication.getName(), tokenProvider.getUsername(accessToken));
        assertNotNull(tokenProvider.getExpirationDate(accessToken));
        assertTrue(tokenProvider.isTokenValid(accessToken, user));
        assertFalse(tokenProvider.isTokenValid(accessToken, user2));
        assertNotNull(tokenProvider.getAuthenticationToken(user));
    }

}