package com.my1project.my1projecg.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Sistemi Bankar API")
                        .version("1.0")
                        .description("Dokumentacioni i API-ve për menaxhimin e transaksioneve bankare."))
                // Shton rregullin që çdo endpoint mund të kërkojë këtë skemë sigurie
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // Konfiguron ekzaktësisht se si do të jetë skema (Bearer JWT)
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}