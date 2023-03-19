package com.rawlabs.spacelabs.domain.dto;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import com.rawlabs.spacelabs.service.CoworkingSpaceService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Guest"
    )
    private GuestRequestDto guest;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1"
    )
    private Long paymentMethodId;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1"
    )
    private Long coworkingSpaceId;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1"
    )
    private Long transactionId;



}
