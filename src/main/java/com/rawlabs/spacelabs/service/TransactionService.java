package com.rawlabs.spacelabs.service;

import com.rawlabs.spacelabs.constant.ErrorCode;
import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import com.rawlabs.spacelabs.domain.dao.Guest;
import com.rawlabs.spacelabs.domain.dao.PaymentMethod;
import com.rawlabs.spacelabs.domain.dao.Transaction;
import com.rawlabs.spacelabs.domain.dto.TransactionRequestDto;
import com.rawlabs.spacelabs.domain.dto.TransactionResponseDto;
import com.rawlabs.spacelabs.exception.SpaceLabsException;
import com.rawlabs.spacelabs.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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

    public TransactionResponseDto inquiry (TransactionRequestDto request){

        log.info("Begin Get coworking space by id :: {}", request.getCoworkingSpaceId());
        CoworkingSpace coworkingSpace = coworkingSpaceService.getById(request.getCoworkingSpaceId());
        if(coworkingSpace == null){
            try {
                throw new Exception();
            } catch (Exception e) {
                log.error("Error get coworking space {}", e);
                throw new SpaceLabsException("Error find payment : ", ErrorCode.DATA_NOT_FOUND.name());
            }
        }
        log.info("Response get coworking space", coworkingSpace);


        log.info("Begin Get payment method by id :: {}", request.getPaymentMethodId());
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(request.getPaymentMethodId());
        if(paymentMethod == null){
            try {
                throw new Exception();
            } catch (Exception e) {
                log.error("Error get paymnet method {}", e);
                throw new SpaceLabsException("Error find payment : ", ErrorCode.DATA_NOT_FOUND.name());
            }
        }
        log.info("Response Get payment method by id", paymentMethod);

        Guest guest;
        try {
            guest = guestService.saveGuest(request.getGuest());
        } catch (Exception e){
            log.error("Error save guest {}", e);
            throw new SpaceLabsException("Error Register", ErrorCode.UNKNOWN_ERROR.name());
        }
        log.info("Response save gues", guest);

        long duration = Duration.between(guest.getTimeStart(), guest.getTimeEnd()).toHours();
        int total = (int) (coworkingSpace.getPrice() * duration);
        log.info("Begin save transaction by guest");
        Transaction transaction;
        try {
            transaction = transactionRepository.save(Transaction.builder()
                    .createdDate(LocalDateTime.now())
                    .isDeleted(Boolean.FALSE)
                    .total(total)
                    .paymentMethod(paymentMethod)
                    .coworkingSpace(coworkingSpace)
                    .guest(guest)
                    .status("PENDING")
                    .build()
            );

            log.info("Save transaction response :: ", transaction);
        } catch (Exception e) {
            log.error("Error save transaction", e);
            throw new SpaceLabsException("Error save transaction", ErrorCode.UNKNOWN_ERROR.name());
        }

        return TransactionResponseDto.builder()
                .transactionId(transaction.getId())
                .status(transaction.getStatus())
                .paymentMethodName(transaction.getPaymentMethod().getName())
                .build();

    }

    public TransactionResponseDto execute(TransactionRequestDto request){

        log.info("Begin get transaction by Id", request.getTransactionId());

        Transaction transaction;

        try {
           transaction =  this.getTransactionById(request.getTransactionId());
            log.info("Response transaction by ID : ", transaction);
        } catch (Exception e) {
            log.error("Error get transaction", e);
            throw new SpaceLabsException("Error get transcation", ErrorCode.DATA_NOT_FOUND.name());
        }

        log.info("Set status transaction to paid");

        transaction.setStatus("PAID");

        transaction = transactionRepository.save(Transaction.builder()
                .status(transaction.getStatus())
                .modifiedDate(LocalDateTime.now())
                .build()
        );


        return TransactionResponseDto.builder()
                .transactionId(transaction.getId())
                .status(transaction.getStatus())
                .paymentMethodName(transaction.getPaymentMethod().getName())
                .build();

    }

    public Transaction getTransactionById(Long id){
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) return null;
        return transactionOptional.get();
    }
}
