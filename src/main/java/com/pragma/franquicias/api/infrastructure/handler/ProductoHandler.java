package com.pragma.franquicias.api.infrastructure.handler;

import com.pragma.franquicias.api.application.dto.StockUpdateRequest;
import com.pragma.franquicias.api.domain.model.Producto;
import com.pragma.franquicias.api.domain.repository.ProductoRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductoHandler {
    private final ProductoRepository repository;

    public ProductoHandler(ProductoRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().body(repository.findAll(), Producto.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(producto -> ServerResponse.ok().bodyValue(producto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Producto.class)
                .flatMap(repository::save)
                .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(Producto.class)
                .flatMap(producto -> repository.update(id, producto))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateStock(ServerRequest request) {
        return request.bodyToMono(StockUpdateRequest.class)
                .flatMap(stockUpdate -> repository.updateStock(stockUpdate.getId(), stockUpdate.getStock()))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.deleteById(id).then(ServerResponse.noContent().build());
    }
}