package com.rahul.lotteryassignment.configurations;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 * This class customizes the OpenAPI documentation
 */
@Configuration
public class SwaggerConfig {

    /**
     * Defines a grouped OpenAPI specification
     *
     * @return a GroupedOpenApi instance with a specific set of API endpoints
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("lottery-api")
                .pathsToMatch("/ticket/**", "/status/**")
                .build();
    }

    /**
     * Configures the global OpenAPI metadata for the application.
     *
     * @return an OpenAPI instance with application-specific metadata
     */
    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info()
                        .title("Lottery Assignment API")
                        .version("1.0")
                        .description("This API handles ticket generation, modification, and status retrieval for our lottery system.")
                        .contact(new Contact()
                                .name("Rahul")
                                .email("readerrahul@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
