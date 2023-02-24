package com.rawlabs.spacelabs.component;

import com.rawlabs.spacelabs.domain.dao.User;
import com.rawlabs.spacelabs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnApplicationStartup.class)
class OnApplicationStartupTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OnApplicationStartup onApplicationStartup;

    @Test
    void onApplicationEvent_Test() {
        when(userRepository.save(any())).thenReturn(User.builder().id(1L).build());
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        verify(userRepository, times(1)).save(any());
        verify(passwordEncoder, times(1)).encode(any());
    }

}