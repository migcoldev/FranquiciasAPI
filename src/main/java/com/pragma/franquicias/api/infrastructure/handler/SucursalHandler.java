package com.pragma.franquicias.api.infrastructure.handler;

import com.pragma.franquicias.api.domain.model.Sucursal;
import com.pragma.franquicias.api.domain.repository.SucursalRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SucursalHandler {
    private final SucursalRepository repository;

    public SucursalHandler(SucursalRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().body(repository.findAll(), Sucursal.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(sucursal -> ServerResponse.ok().bodyValue(sucursal))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Sucursal.class)
                .flatMap(repository::save)
                .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(Sucursal.class)
                .flatMap(sucursal -> repository.update(id, sucursal))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.deleteById(id).then(ServerResponse.noContent().build());
    }
}