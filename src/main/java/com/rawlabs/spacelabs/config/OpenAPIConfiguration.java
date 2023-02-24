package com.rawlabs.spacelabs.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes("bearer",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                )
                .info(
                        new Info()
                                .title("Rawlabs.ID")
                                .description("Rawlabs Co-Working Space")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Maverick")
                                                .email("maverick@mail.com")
                                                .url("https://piinalpin.com/")
                                )
                );
    }

}
