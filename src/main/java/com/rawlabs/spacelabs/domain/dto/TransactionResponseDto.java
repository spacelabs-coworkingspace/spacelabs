package com.rawlabs.spacelabs.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1"
    )
    private Long transactionId;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "PENDING/PAID"
    )
    private String status;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "BCA"
    )
    private String paymentMethodName;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "10000"
    )
    private int total;
}
