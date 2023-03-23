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
public class TransactionExecuteDto {

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1"
    )
    private Long transactionId;

}
