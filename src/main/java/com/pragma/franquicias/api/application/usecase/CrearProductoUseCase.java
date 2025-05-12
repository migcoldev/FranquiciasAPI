package com.pragma.franquicias.api.application.usecase;

import com.pragma.franquicias.api.domain.model.Producto;
import com.pragma.franquicias.api.domain.repository.ProductoRepository;
import reactor.core.publisher.Mono;

public class CrearProductoUseCase {
    private final ProductoRepository productoRepository;

    public CrearProductoUseCase(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Mono<Producto> execute(Producto producto) {
        return productoRepository.save(producto);
    }
}