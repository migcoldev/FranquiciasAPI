package com.pragma.franquicias.api.application.usecase;

import com.pragma.franquicias.api.domain.repository.ProductoRepository;
import reactor.core.publisher.Mono;

public class EliminarProductoUseCase {
    private final ProductoRepository productoRepository;

    public EliminarProductoUseCase(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Mono<Void> execute(String id) {
        return productoRepository.deleteById(id);
    }
}
