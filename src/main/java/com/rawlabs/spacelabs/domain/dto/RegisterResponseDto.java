package com.rawlabs.spacelabs.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDto {

    @Schema(
            requiredMode =   Schema.RequiredMode.REQUIRED,
            example = "user"
    )
    private String username;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "mail@mail.com"
    )
    private String email;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "password"
    )
    @JsonIgnore
    private String password;
}
