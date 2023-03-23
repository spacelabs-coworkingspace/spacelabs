package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.Guest;
import com.rawlabs.spacelabs.domain.dto.GuestRequestDto;
import com.rawlabs.spacelabs.repository.GuestRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GuestService.class)
public class GuestServiceTest {

    @MockBean
    private GuestRepository guestRepository;

    @Autowired
    private GuestService guestService;

    @Test
    void saveGuest_Success(){
            when(guestRepository.save(Guest.builder().build())).thenReturn(
                    Guest.builder()
                            .id(1L)
                            .fullName("Any")
                            .phoneNumber("AnyPhonenumber")
                            .email("mail@mail.com")
                            .date(LocalDate.now())
                            .timeStart(LocalTime.now())
                            .timeEnd(LocalTime.now().plusHours(1))
                            .isDeleted(Boolean.FALSE)
                            .build()
            );

            Guest guest = guestService.saveGuest(GuestRequestDto.builder()
                            .fullName("John Doe")
                    .phoneNumber("AnyPhonenumber")
                    .email("mail@mail.com")
                    .date(LocalDate.now())
                    .timeStart(LocalTime.now())
                    .timeEnd(LocalTime.now().plusHours(1))
                    .build());





//        Assertions.assertNotNull(guest);


    }
}
