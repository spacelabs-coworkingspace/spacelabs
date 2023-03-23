package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import com.rawlabs.spacelabs.domain.dao.Guest;
import com.rawlabs.spacelabs.domain.dao.PaymentMethod;
import com.rawlabs.spacelabs.domain.dao.Transaction;
import com.rawlabs.spacelabs.domain.dto.GuestRequestDto;
import com.rawlabs.spacelabs.domain.dto.TransactionExecuteDto;
import com.rawlabs.spacelabs.domain.dto.TransactionInquiryDto;
import com.rawlabs.spacelabs.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TransactionService.class)
class TransactionServiceTest {
    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private GuestService guestService;

    @MockBean
    private CoworkingSpaceService coworkingSpaceService;

    @MockBean
    private PaymentMethodService paymentMethodService;

    @Autowired
    private TransactionService transactionService;

    @Test
    void inquiry_Success_Test() {
        when(coworkingSpaceService.getById(anyLong())).thenReturn(CoworkingSpace.builder()
                .id(1L)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .isDeleted(Boolean.FALSE)
                .name("Any")
                .price(1000)
                .address("Diponegoro street number 40")
                .build());
        when(paymentMethodService.getPaymentMethodById(anyLong())).thenReturn(PaymentMethod.builder()
                .id(1L)
                .name("Any")
                .instruction("any")
                .build());
        when(guestService.saveGuest(any())).thenReturn(Guest.builder()
                .id(1L)
                .fullName("John Doe")
                .phoneNumber("any phone")
                .email("any mail")
                .date(LocalDate.now())
                .timeStart(LocalTime.now())
                .timeEnd(LocalTime.now().plusHours(8))
                .build());
        when(transactionRepository.save(any())).thenReturn(Transaction.builder()
                .transactionId(1L)
                .createdDate(LocalDateTime.now())
                .isDeleted(Boolean.FALSE)
                .total(10000)
                .guest(Guest.builder().build())
                .coworkingSpace(CoworkingSpace.builder().build())
                .status("PENDING")
                .build()
        );

        Transaction transaction = transactionService.inquiry(TransactionInquiryDto.builder()
                .coworkingSpaceId(1L)
                .paymentMethodId(1L)
                .guest(GuestRequestDto.builder().build())
                .build());

        assertNotNull(transaction);
        assertEquals(1L, transaction.getTransactionId());
        assertEquals("PENDING", transaction.getStatus());
    }

    @Test
    void inquiry_PaymentMethodNotFound_Test() {
        when(coworkingSpaceService.getById(anyLong())).thenReturn(CoworkingSpace.builder()
                .id(1L)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .isDeleted(Boolean.FALSE)
                .name("Any")
                .price(1000)
                .address("Diponegoro street number 40")
                .build());
        when(paymentMethodService.getPaymentMethodById(anyLong())).thenReturn(null);

        assertThrows(Exception.class, () -> transactionService.inquiry(TransactionInquiryDto.builder()
                .coworkingSpaceId(1L)
                .paymentMethodId(1L)
                .guest(GuestRequestDto.builder().build())
                .build()));
    }

    @Test
    void inquiry_CoworkingSpaceNotFound_Test() {
        when(coworkingSpaceService.getById(anyLong())).thenReturn(null);
        assertThrows(Exception.class, () -> transactionService.inquiry(TransactionInquiryDto.builder()
                .coworkingSpaceId(1L)
                .paymentMethodId(1L)
                .guest(GuestRequestDto.builder().build())
                .build()));
    }

    @Test
    void execute_Success_Test() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(Transaction.builder()
                .transactionId(1L)
                .createdDate(LocalDateTime.now())
                .isDeleted(Boolean.FALSE)
                .total(10000)
                .guest(Guest.builder().build())
                .coworkingSpace(CoworkingSpace.builder().build())
                .status("PENDING")
                .build()
        ));
        when(transactionRepository.save(any())).thenReturn(Transaction.builder()
                .transactionId(1L)
                .createdDate(LocalDateTime.now())
                .isDeleted(Boolean.FALSE)
                .total(10000)
                .guest(Guest.builder().build())
                .coworkingSpace(CoworkingSpace.builder().build())
                .status("PAID")
                .build()
        );

        Transaction transaction = transactionService.execute(TransactionExecuteDto.builder().transactionId(1L).build());
        assertNotNull(transaction);
        assertEquals(1L, transaction.getTransactionId());
        assertEquals("PAID", transaction.getStatus());
    }

    @Test
    void execute_TransactionNotFound_Test() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> transactionService.execute(TransactionExecuteDto.builder().transactionId(1L).build()));
    }

}
