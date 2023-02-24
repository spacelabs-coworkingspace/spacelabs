package com.rawlabs.spacelabs.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rawlabs.spacelabs.constant.DateTimePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "anyToken")
    private String accessToken;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimePattern.DEFAULT_DATE_TIME_PATTERN)
    private LocalDateTime expiresIn;

}
