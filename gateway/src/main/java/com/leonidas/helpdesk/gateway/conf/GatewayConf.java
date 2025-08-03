package com.leonidas.helpdesk.gateway.conf;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConf {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route("user", route -> route
//                        .path("api/user/**")
//                        .uri("http://localhost:8082/"))
                .route("ticket", route -> route
                        .path("/api/ticket")
                        .uri("http://localhost:8083"))
//                .route("auth", route -> route
//                        .path("api/auth/**")
//                        .uri("http://localhost:8082/"))
                .route("cep - Brasilapi", route -> route
                        .path("/cep/v1/**")
                        .filters(filter -> filter.rewritePath("/cep/v1/(?<cep>.*)", "/api/cep/v1/${cep}"))
                        .uri("https://brasilapi.com.br"))
                .route("cep - Viacep", route -> route
                        .path("/cep/v2/**")
                        .filters(filter -> filter.rewritePath("/cep/v2/(?<cep>.*)", "/ws/${cep}/json"))
                        .uri("https://viacep.com.br"))
                .build();
    }

}
