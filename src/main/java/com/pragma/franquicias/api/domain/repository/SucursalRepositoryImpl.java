package com.pragma.franquicias.api.domain.repository;

import com.pragma.franquicias.api.domain.model.Sucursal;
import com.pragma.franquicias.api.infrastructure.adapter.SucursalDynamoDbAdapter;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class SucursalRepositoryImpl implements SucursalRepository {

    private final SucursalDynamoDbAdapter sucursalAdapter;

    public SucursalRepositoryImpl(SucursalDynamoDbAdapter sucursalAdapter) {
        this.sucursalAdapter = sucursalAdapter;
    }

    @Override
    public Mono<Sucursal> save(Sucursal sucursal) {
        return sucursalAdapter.save(sucursal);
    }

    @Override
    public Mono<Sucursal> update(String id, Sucursal sucursal) {
        return sucursalAdapter.update(id, sucursal);
    }

    @Override
    public Mono<Sucursal> findById(String id) {
        return sucursalAdapter.findById(id);
    }

    @Override
    public Flux<Sucursal> findAll() {
        return sucursalAdapter.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return sucursalAdapter.deleteById(id);
    }
}