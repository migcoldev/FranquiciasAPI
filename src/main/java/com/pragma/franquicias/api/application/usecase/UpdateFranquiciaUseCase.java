package com.pragma.franquicias.api.application.usecase;

import com.pragma.franquicias.api.domain.model.Franquicia;
import com.pragma.franquicias.api.domain.repository.FranquiciaRepository;
import reactor.core.publisher.Mono;

public class UpdateFranquiciaUseCase {
    private final FranquiciaRepository franquiciaRepository;

    public UpdateFranquiciaUseCase(FranquiciaRepository franquiciaRepository) {
        this.franquiciaRepository = franquiciaRepository;
    }

    public Mono<Franquicia> execute(String id, Franquicia franquicia) {
        return franquiciaRepository.update(id, franquicia);
    }
}
