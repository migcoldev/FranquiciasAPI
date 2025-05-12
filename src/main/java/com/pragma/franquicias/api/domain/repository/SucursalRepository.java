package com.pragma.franquicias.api.domain.repository;

import com.pragma.franquicias.api.domain.model.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalRepository {
    Mono<Sucursal> update(String id, Sucursal sucursal);
    Mono<Sucursal> save(Sucursal producto);
    Mono<Sucursal> findById(String id);
    Flux<Sucursal> findAll();
    Mono<Void> deleteById(String id);
}