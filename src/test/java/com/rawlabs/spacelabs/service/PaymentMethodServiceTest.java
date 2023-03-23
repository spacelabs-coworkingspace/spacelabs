package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.PaymentMethod;
import com.rawlabs.spacelabs.domain.dto.PaymentMethodRequestDto;
import com.rawlabs.spacelabs.domain.dto.PaymentMethodResponseDto;
import com.rawlabs.spacelabs.repository.PaymentMethodRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PaymentMethodService.class)
class PaymentMethodServiceTest {

    @MockBean
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Test
    void findPaymentMethod_Success_Test() {

        when(paymentMethodRepository.findPaymentMethodByNameIgnoreCase(any())).thenReturn(PaymentMethod.builder()
                .name("any name")
                .instruction("any instruction")
                .build()

        );

        PaymentMethodResponseDto response = paymentMethodService.findPaymentMethod(PaymentMethodRequestDto.builder()
                        .name("any Name")
                .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals("any name", response.getName());


    }

    @Test
    void findPaymentMethod_Failed_Test(){
        when(paymentMethodRepository.findPaymentMethodByNameIgnoreCase(any())).thenReturn(null);
        Assertions.assertThrows(Exception.class , ()->paymentMethodService.findPaymentMethod(PaymentMethodRequestDto.builder().name("any").build()) );
    }

    @Test
    void getPaymentMethodById_Success_Test(){
        when(paymentMethodRepository.findById(anyLong())).thenReturn(Optional.of(PaymentMethod.builder()
                        .id(1L)
                        .name("Any")
                .build()));
       PaymentMethod  paymentMethod = paymentMethodService.getPaymentMethodById(1L);
       Assertions.assertNotNull(paymentMethod);
       Assertions.assertEquals(1L, paymentMethod.getId());
       Assertions.assertEquals("Any", paymentMethod.getName());

       when(paymentMethodRepository.findById(anyLong())).thenReturn(Optional.empty());
       paymentMethod = paymentMethodService.getPaymentMethodById(1L);
       Assertions.assertNull(paymentMethod);


    }


    @Test
    void getPaymentMethods_Success_Test(){
        when(paymentMethodRepository.findAll()).thenReturn(List.of(
                PaymentMethod.builder().id(1L).build()
        ));
        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethods();

        Assertions.assertFalse(paymentMethods.isEmpty());


    }

}
