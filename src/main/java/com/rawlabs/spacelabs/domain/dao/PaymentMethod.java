package com.rawlabs.spacelabs.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
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
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1"
    )
    private Long id;

    @Column(name = "name", nullable = false)
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "BCA"
    )
    private String name;

    @Column(name = "instuction")
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Any instruction here"
    )
    private String instruction;

    @Column(name = "is_deleted", nullable = false)
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "false"
    )
    private Boolean isDeleted;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Transaction> transaction;
}
