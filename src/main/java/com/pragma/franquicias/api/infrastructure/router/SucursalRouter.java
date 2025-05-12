package com.pragma.franquicias.api.infrastructure.router;

import com.pragma.franquicias.api.infrastructure.handler.SucursalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SucursalRouter {
    @Bean(name = "sucursalRoute")
    public RouterFunction<ServerResponse> route(SucursalHandler handler) {
        return RouterFunctions.route()
                .GET("/sucursales", handler::getAll)
                .GET("/sucursales/{id}", handler::getById)
                .POST("/sucursales", handler::create)
                .DELETE("/sucursales/{id}", handler::delete)
                .build();
    }
}