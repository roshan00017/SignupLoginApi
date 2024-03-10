package org.example.signuplogin.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerOpenApiConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("SpringBoot API")
                        .description("Signup Login Service")
                        .version("v1.0.0")
                        .license(new License().name("Roshan Gyawali").url("https://github.com/roshan00017")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot Wiki Documentation")
                        .url("https://springboot.wiki.github.org/docs"));
    }


}