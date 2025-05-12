package com.pragma.franquicias.api.infrastructure.router;

import com.pragma.franquicias.api.infrastructure.handler.FranquiciaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FranquiciaRouter {
    @Bean(name = "franquiciaRoute")
    public RouterFunction<ServerResponse> route(FranquiciaHandler handler) {
        return RouterFunctions.route()
                .GET("/franquicias", handler::getAll)
                .GET("/franquicias/{id}", handler::getById)
                .POST("/franquicias", handler::create)
                .DELETE("/franquicias/{id}", handler::delete)
                .build();
    }
}