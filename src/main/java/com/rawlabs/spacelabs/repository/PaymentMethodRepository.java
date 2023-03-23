package com.rawlabs.spacelabs.repository;

import com.rawlabs.spacelabs.domain.dao.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    PaymentMethod findPaymentMethodByNameIgnoreCase(String name);
}
