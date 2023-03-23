package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.PaymentMethod;
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
public class PaymentMethodServiceTest {

    @MockBean
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Test
    void findPaymentMethod_Success_Test() {

        when(paymentMethodRepository.save(any())).thenReturn(PaymentMethod.builder()
                .name("any name")
                        .instruction("any instruction")
                .build()

        );

    }

    @Test
    void getPaymentMethodById_Success_Test(){
        when(paymentMethodRepository.findById(anyLong())).thenReturn(Optional.ofNullable(PaymentMethod.builder().build()));
    }

    @Test
    void getPaymentMethods_Success_Test(){
        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethods();

        Assertions.assertNotNull(paymentMethods);
    }

}
