package com.pragma.franquicias.api.infrastructure.handler;

import com.pragma.franquicias.api.domain.model.Franquicia;
import com.pragma.franquicias.api.domain.repository.FranquiciaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FranquiciaHandler {
    private final FranquiciaRepository repository;

    public FranquiciaHandler(FranquiciaRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().body(repository.findAll(), Franquicia.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(franquicia -> ServerResponse.ok().bodyValue(franquicia))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Franquicia.class)
                .flatMap(repository::save)
                .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(Franquicia.class)
                .flatMap(franquicia -> repository.update(id, franquicia))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.deleteById(id).then(ServerResponse.noContent().build());
    }
}