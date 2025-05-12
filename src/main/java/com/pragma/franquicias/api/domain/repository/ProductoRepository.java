package com.pragma.franquicias.api.domain.repository;

import com.pragma.franquicias.api.domain.model.Producto;
import com.pragma.franquicias.api.domain.model.ProductoConSucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository {
    Mono<Producto> update(String id, Producto producto);
    Mono<Producto> save(Producto producto);
    Mono<Producto> findById(String id);
    Flux<Producto> findAll();
    Mono<Void> deleteById(String id);
    Mono<Producto> updateStock(String id, int stock);
    Flux<ProductoConSucursal> getMaxStockBySucursal();
}