package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import com.rawlabs.spacelabs.repository.CoworkingSpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CoworkingSpaceService.class)
public class CoworkingSpaceServiceTest {
    @MockBean
    private CoworkingSpaceRepository coworkingSpaceRepository;

    @Autowired
    private CoworkingSpaceService coworkingSpaceService;

    @Test
    void getById_Success_Test(){
        when(coworkingSpaceRepository.findById(anyLong())).thenReturn(Optional.of(CoworkingSpace.builder()
                        .id(1L)
                        .createdDate(LocalDateTime.now())
                        .isDeleted(Boolean.FALSE)
                        .name("Any")
                        .price(10000)
                        .address("Jakal km 14.5")
                .build()));

        Optional<CoworkingSpace> coworkingSpace = Optional.ofNullable(coworkingSpaceService.getById(1L));
        if(coworkingSpace.isEmpty()){
            assertNull(coworkingSpace);
        } else {
            assertNotNull(coworkingSpace);
        }
    }

    @Test
    void deleteById_Success_Test(){
        coworkingSpaceRepository.deleteById(anyLong());
    }

    @Test
    void getCoworkingSpaces_Success_Test(){
       when(coworkingSpaceRepository.findCoworkingSpaceByAddressIgnoreCase(any())).thenReturn(List.of(CoworkingSpace.builder().build()));

        when(coworkingSpaceRepository.findCoworkingSpaceByNameIgnoreCase(any())).thenReturn(List.of(CoworkingSpace.builder().build()));

        when(coworkingSpaceRepository.findCoworkingSpaceByNameAndAddressIgnoreCase(any(), any())).thenReturn(List.of(CoworkingSpace.builder().build()));

        when(coworkingSpaceRepository.findAll()).thenReturn(List.of(CoworkingSpace.builder().build()));
    }



}
