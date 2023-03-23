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

    public PaymentMethodResponseDto findPaymentMethod(PaymentMethodRequestDto request) {
        try {
            PaymentMethod paymentMethod = paymentMethodRepository.findPaymentMethodByNameIgnoreCase(request.getName());

            if (paymentMethod == null) throw new SpaceLabsException("Payment method not found", ErrorCode.DATA_NOT_FOUND.name());
            return PaymentMethodResponseDto.builder()
                    .name(paymentMethod.getName())
                    .build();
        } catch (Exception e) {
            log.error("Happened error when get payment method by name. ", e);
            throw e;
        }
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
