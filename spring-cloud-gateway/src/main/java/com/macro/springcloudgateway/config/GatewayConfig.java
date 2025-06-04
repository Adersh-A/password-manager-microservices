package com.macro.springcloudgateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class GatewayConfig {

    @Value("${services.auth-service-url}")
    private String authServiceUrl;

    @Value("${services.password-service-url}")
    private String passwordServiceUrl;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_route", r -> r
                        .path("/api/v1/authservice")
                        .filters(f -> f.rewritePath("/api/v1/authservice", "/api/auth/google"))
                        .uri(authServiceUrl))
                .route("passwords_route", r -> r
                        .path("/api/v1/passwords")
                        .filters(f->f.rewritePath("/api/v1/passwords","/api/passwords"))
                        .uri(passwordServiceUrl))
                .build();
    }
}