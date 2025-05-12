package com.pragma.franquicias.api.application.usecase;

import com.pragma.franquicias.api.domain.model.Franquicia;
import com.pragma.franquicias.api.domain.repository.FranquiciaRepository;
import reactor.core.publisher.Mono;

public class CrearProductoUseCase {
    private final FranquiciaRepository franquiciaRepository;

    public CrearProductoUseCase(FranquiciaRepository franquiciaRepository) {
        this.franquiciaRepository = franquiciaRepository;
    }

    public Mono<Franquicia> execute(Franquicia franquicia) {
        return franquiciaRepository.save(franquicia);
    }
}