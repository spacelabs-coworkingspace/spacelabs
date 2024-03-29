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
class CoworkingSpaceServiceTest {
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

        CoworkingSpace coworkingSpace = coworkingSpaceService.getById(1L);
        assertNotNull(coworkingSpace);

        when(coworkingSpaceRepository.findById(anyLong())).thenReturn(Optional.empty());

        coworkingSpace = coworkingSpaceService.getById(1L);
        assertNull(coworkingSpace);

    }

    @Test
    void deleteById_Success_Test(){
        doNothing().when(coworkingSpaceRepository).deleteById(anyLong());

        coworkingSpaceService.deleteById(1L);

        verify(coworkingSpaceRepository, times(1)).deleteById(anyLong());

    }

    @Test
    void getCoworkingSpaces_Success_Test(){
        when(coworkingSpaceRepository.findCoworkingSpaceByAddressIgnoreCase(any())).thenReturn(List.of(CoworkingSpace.builder().id(1L).build()));

        when(coworkingSpaceRepository.findCoworkingSpaceByNameIgnoreCase(any())).thenReturn(List.of(CoworkingSpace.builder().id(1L).build()));

        when(coworkingSpaceRepository.findCoworkingSpaceByNameAndAddressIgnoreCase(any(), any())).thenReturn(List.of(CoworkingSpace.builder().id(1L).build()));

        when(coworkingSpaceRepository.findAll()).thenReturn(List.of(CoworkingSpace.builder().id(1L).build()));

        List<CoworkingSpace> coworkingSpaces = coworkingSpaceService.getCoworkingSpaces(null, null);
        assertFalse(coworkingSpaces.isEmpty());

        coworkingSpaces = coworkingSpaceService.getCoworkingSpaces( null, "any Name");
        assertFalse(coworkingSpaces.isEmpty());

        coworkingSpaces = coworkingSpaceService.getCoworkingSpaces( "Any address", null);
        assertFalse(coworkingSpaces.isEmpty());

        coworkingSpaces = coworkingSpaceService.getCoworkingSpaces( "Any address", "Any name");
        assertFalse(coworkingSpaces.isEmpty());

    }



}
