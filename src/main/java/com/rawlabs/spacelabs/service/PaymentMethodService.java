package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.constant.ErrorCode;
import com.rawlabs.spacelabs.domain.dao.PaymentMethod;
import com.rawlabs.spacelabs.domain.dto.PaymentMethodRequestDto;
import com.rawlabs.spacelabs.domain.dto.PaymentMethodResponseDto;
import com.rawlabs.spacelabs.exception.SpaceLabsException;
import com.rawlabs.spacelabs.repository.PaymentMethodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    @Autowired
    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethodResponseDto findPaymentMethod(PaymentMethodRequestDto requst) {
        PaymentMethod paymentMethod = paymentMethodRepository.findPaymentMethodByNameIgnoreCase(requst.getName());

        if(paymentMethod == null){
            try {
                throw new Exception();
            } catch (Exception e) {
                throw new SpaceLabsException("Error find payment : ", ErrorCode.DATA_NOT_FOUND.name());
            }
        }

        return PaymentMethodResponseDto.builder()
                .name(paymentMethod.getName())
                .build();
    }

    public PaymentMethod getPaymentMethodById(Long id){
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if(paymentMethodOptional.isEmpty()) return null;
        return paymentMethodOptional.get();
    }


    public List<PaymentMethod> getPaymentMethods(){
        return paymentMethodRepository.findAll();
    }
}
