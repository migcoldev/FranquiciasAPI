package com.pragma.franquicias.api.domain.repository;

import com.pragma.franquicias.api.domain.model.Producto;
import com.pragma.franquicias.api.domain.model.ProductoConSucursal;
import com.pragma.franquicias.api.infrastructure.adapter.ProductoDynamoDbAdapter;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductoRepositoryImpl implements ProductoRepository {

    private final ProductoDynamoDbAdapter productoAdapter;

    public ProductoRepositoryImpl(ProductoDynamoDbAdapter productoAdapter) {
        this.productoAdapter = productoAdapter;
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return productoAdapter.save(producto);
    }

    @Override
    public Mono<Producto> update(String id, Producto producto) {
        return productoAdapter.update(id, producto);
    }

    @Override
    public Mono<Producto> findById(String id) {
        return productoAdapter.findById(id);
    }

    @Override
    public Flux<Producto> findAll() {
        return productoAdapter.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return productoAdapter.deleteById(id);
    }

    @Override
    public Mono<Producto> updateStock(String id, int stock) {
        return productoAdapter.updateStock(id, stock);
    }

    @Override
    public Flux<ProductoConSucursal> getMaxStockBySucursal() {
        return productoAdapter.getMaxStockBySucursal();
    }
}