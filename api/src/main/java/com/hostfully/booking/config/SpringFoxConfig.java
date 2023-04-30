package com.hostfully.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hostfully.booking.resources"))
                .build()
            .apiInfo(apiInfo())
            .tags(new Tag("Reservations", "Manage your bookings and blocks"));
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Booking API")
                .description("API to manage your bookings")
                .version("1.0.0")
                .build();

    }

}