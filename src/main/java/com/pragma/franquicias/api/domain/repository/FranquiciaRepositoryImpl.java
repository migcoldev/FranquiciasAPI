package com.pragma.franquicias.api.domain.repository;

import com.pragma.franquicias.api.domain.model.Franquicia;
import com.pragma.franquicias.api.infrastructure.adapter.FranquiciaDynamoDbAdapter;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class FranquiciaRepositoryImpl implements FranquiciaRepository {

    private final FranquiciaDynamoDbAdapter franquiciaAdapter;

    public FranquiciaRepositoryImpl(FranquiciaDynamoDbAdapter franquiciaAdapter) {
        this.franquiciaAdapter = franquiciaAdapter;
    }

    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        return franquiciaAdapter.save(franquicia);
    }

    @Override
    public Mono<Franquicia> update(String id, Franquicia franquicia) {
        return franquiciaAdapter.update(id, franquicia);
    }

    @Override
    public Mono<Franquicia> findById(String id) {
        return franquiciaAdapter.findById(id);
    }

    @Override
    public Flux<Franquicia> findAll() {
        return franquiciaAdapter.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return franquiciaAdapter.deleteById(id);
    }
}