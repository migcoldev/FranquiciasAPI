package com.pragma.franquicias.api.domain.repository;

import com.pragma.franquicias.api.domain.model.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaRepository {
    Mono<Franquicia> update(String id, Franquicia franquicia);
    Mono<Franquicia> save(Franquicia franquicia);
    Mono<Franquicia> findById(String id);
    Flux<Franquicia> findAll();
    Mono<Void> deleteById(String id);
}