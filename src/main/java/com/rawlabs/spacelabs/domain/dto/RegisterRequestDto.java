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
public class RegisterRequestDto {


    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "John Dhoe"
    )
    private String fullName;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "password"
    )
    private String password;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "johndhoe@mail.com"
    )
    private String email;
}
