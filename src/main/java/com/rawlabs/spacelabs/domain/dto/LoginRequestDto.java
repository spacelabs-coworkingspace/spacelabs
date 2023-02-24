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
public class LoginRequestDto {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "user")
    private String username;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "password")
    private String password;

}
