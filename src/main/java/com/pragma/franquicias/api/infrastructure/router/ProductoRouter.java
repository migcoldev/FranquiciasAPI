package com.pragma.franquicias.api.infrastructure.router;

import com.pragma.franquicias.api.infrastructure.handler.ProductoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductoRouter {
    @Bean(name = "productoRoute")
    public RouterFunction<ServerResponse> route(ProductoHandler handler) {
        return RouterFunctions.route()
                .GET("/productos", handler::getAll)
                .GET("/productos/{id}", handler::getById)
                .POST("/productos", handler::create)
                .PUT("/productos/{id}", handler::update)
                .DELETE("/productos/{id}", handler::delete)
                .PATCH("/productos/update-stock", handler::updateStock)
                .build();
    }
}