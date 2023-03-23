package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import com.rawlabs.spacelabs.domain.dao.Guest;
import com.rawlabs.spacelabs.domain.dao.PaymentMethod;
import com.rawlabs.spacelabs.domain.dao.Transaction;
import com.rawlabs.spacelabs.domain.dto.TransactionExecuteDto;
import com.rawlabs.spacelabs.domain.dto.TransactionInquiryDto;
import com.rawlabs.spacelabs.exception.SpaceLabsException;
import com.rawlabs.spacelabs.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.rawlabs.spacelabs.constant.ErrorCode.DATA_NOT_FOUND;

@Slf4j
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final GuestService guestService;
    private final CoworkingSpaceService coworkingSpaceService;
    private final PaymentMethodService paymentMethodService;


    @Autowired
    public TransactionService(TransactionRepository transactionRepository, GuestService guestService, CoworkingSpaceService coworkingSpaceService, PaymentMethodService paymentMethodService) {
        this.transactionRepository = transactionRepository;
        this.guestService = guestService;
        this.coworkingSpaceService = coworkingSpaceService;
        this.paymentMethodService = paymentMethodService;
    }

    public Transaction inquiry(TransactionInquiryDto request) {
        try {
            log.info("Begin Get coworking space by id :: {}", request.getCoworkingSpaceId());
            CoworkingSpace coworkingSpace = coworkingSpaceService.getById(request.getCoworkingSpaceId());
            if (coworkingSpace == null) {
                throw new SpaceLabsException("Coworking space not found", DATA_NOT_FOUND.name());
            }
            log.info("Response get coworking space {}", coworkingSpace);

            log.info("Begin Get payment method by id :: {}", request.getPaymentMethodId());
            PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(request.getPaymentMethodId());
            if (paymentMethod == null) {
                throw new SpaceLabsException("Payment method space not found", DATA_NOT_FOUND.name());
            }
            log.info("Response Get payment method by id {}", paymentMethod);

            log.info("Begin save guest");
            Guest guest = guestService.saveGuest(request.getGuest());
            log.info("Response save guest :: {}", guest);


            long duration = Duration.between(guest.getTimeStart(), guest.getTimeEnd()).toHours();
            int total = coworkingSpace.getPrice() * (int) duration;
            log.info("Begin save transaction by guest");

            Transaction transaction = transactionRepository.save(Transaction.builder()
                    .createdDate(LocalDateTime.now())
                    .isDeleted(Boolean.FALSE)
                    .total(total)
                    .paymentMethod(paymentMethod)
                    .coworkingSpace(coworkingSpace)
                    .guest(guest)
                    .status("PENDING")
                    .build()
            );

            log.info("Save transaction response :: {}", transaction);
            return transaction;
        } catch (Exception e) {
            log.error("Error get payment method ", e);
            throw e;
        }

    }

    public Transaction execute(TransactionExecuteDto request) {
        try {
            log.info("Begin get transaction by Id :: {}", request.getTransactionId());

            Optional<Transaction> transactionOptional = transactionRepository.findById(request.getTransactionId());
            if (transactionOptional.isEmpty()) throw new SpaceLabsException("Transaction not found", DATA_NOT_FOUND.name());

            Transaction transaction = transactionOptional.get();
            log.info("Response transaction by ID : {}", transaction);

            log.info("Set status transaction to paid");
            transaction.setStatus("PAID");
            transaction.setModifiedDate(LocalDateTime.now());
            transaction = transactionRepository.save(transaction);

            return transaction;
        } catch (Exception e) {
            log.error("Error get transaction", e);
            throw e;
        }
    }

}
