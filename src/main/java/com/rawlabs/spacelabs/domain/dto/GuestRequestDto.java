package com.rawlabs.spacelabs.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestRequestDto {

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "John Doe"
    )
    private String fullName;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "081123456"
    )
    private String phoneNumber;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "johndoe@mail.com"
    )
    private String email;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "21-03-2023"
    )
    private LocalDate date;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "09:00"
    )
    private LocalTime timeStart;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "11:00"
    )
    private LocalTime timeEnd;
}
