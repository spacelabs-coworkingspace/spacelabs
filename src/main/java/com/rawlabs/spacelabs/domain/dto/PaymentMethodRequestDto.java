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
public class PaymentMethodRequestDto {

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "BCA"
    )
    private String name;
}
