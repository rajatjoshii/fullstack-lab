package com.example.mybackend.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "bearerAuth",                   // name to use in annotations
    type = SecuritySchemeType.HTTP,        // type = HTTP auth
    scheme = "bearer",                     // uses Bearer token
    bearerFormat = "JWT"                   // format = JWT
)
public class SwaggerConfig {
}
