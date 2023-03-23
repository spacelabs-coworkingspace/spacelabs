package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import com.rawlabs.spacelabs.domain.dao.Guest;
import com.rawlabs.spacelabs.domain.dao.PaymentMethod;
import com.rawlabs.spacelabs.domain.dao.Transaction;
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


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TransactionService.class)
public class TransactionServiceTest {
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
    void inquiry_Success_Test(){
        when(coworkingSpaceService.getById(anyLong())).thenReturn(CoworkingSpace.builder()
                .id(1L)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .isDeleted(Boolean.FALSE)
                .name("Any")
                .price(1000)
                .address("Diponegoro street numberr 40")
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
                .timeEnd(LocalTime.now().plusHours(1))
                .build());

        when(transactionRepository.save(any())).thenReturn(Transaction.builder()
                .id(1L)
                .createdDate(LocalDateTime.now())
                .isDeleted(Boolean.FALSE)
                .total(12000)
                .guest(Guest.builder().build())
                .status("PENDING")
                .build()
        );
    }


    @Test
    void execute_Success_Test(){

        when(transactionRepository.findById(any())).thenReturn(Optional.ofNullable(Transaction.builder()
                        .id(1L)
                .build()));
        Transaction transcation = transactionService.getTransactionById(1l);

       transcation.setStatus("PAID");
       transcation.setModifiedDate(LocalDateTime.now());

       when(transactionRepository.save(transcation)).thenReturn(Transaction.builder()
                       .id(1L)
                       .status(transcation.getStatus())
               .build());
    }
}
