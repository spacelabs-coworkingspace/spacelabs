package com.rawlabs.spacelabs.domain.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
@SQLDelete(sql = "update transaction set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long transactionId;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "total")
    private  int total;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    private Guest guest;

    @ManyToOne
    private PaymentMethod paymentMethod;

    @ManyToOne
    private CoworkingSpace coworkingSpace;

}
