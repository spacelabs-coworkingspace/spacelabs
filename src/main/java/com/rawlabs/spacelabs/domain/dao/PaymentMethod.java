package com.rawlabs.spacelabs.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_method")
@SQLDelete(sql = "update payment_method set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "instuction")
    private String instruction;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL)
    private List<Transaction> transaction;
}
