package com.pragma.franquicias.api.application.usecase;

import com.pragma.franquicias.api.domain.model.Sucursal;
import com.pragma.franquicias.api.domain.repository.SucursalRepository;
import reactor.core.publisher.Mono;

public class CrearSucursalUseCase {
    private final SucursalRepository sucursalRepository;

    public CrearSucursalUseCase(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public Mono<Sucursal> execute(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }
}