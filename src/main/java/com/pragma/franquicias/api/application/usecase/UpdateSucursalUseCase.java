package com.pragma.franquicias.api.application.usecase;

import com.pragma.franquicias.api.domain.model.Sucursal;
import com.pragma.franquicias.api.domain.repository.SucursalRepository;
import reactor.core.publisher.Mono;

public class UpdateSucursalUseCase {
    private final SucursalRepository sucursalRepository;

    public UpdateSucursalUseCase(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public Mono<Sucursal> execute(String id, Sucursal sucursal) {
        return sucursalRepository.update(id, sucursal);
    }
}
