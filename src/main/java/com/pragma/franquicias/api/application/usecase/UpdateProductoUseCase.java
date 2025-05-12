package com.pragma.franquicias.api.application.usecase;

import com.pragma.franquicias.api.domain.model.Producto;
import com.pragma.franquicias.api.domain.repository.ProductoRepository;
import reactor.core.publisher.Mono;

public class UpdateProductoUseCase {
    private final ProductoRepository productoRepository;

    public UpdateProductoUseCase(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Mono<Producto> execute(String id, Producto producto) {
        return productoRepository.update(id, producto);
    }
}
