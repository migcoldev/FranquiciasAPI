package com.pragma.franquicias.api.domain.repository;

import com.pragma.franquicias.api.domain.model.Sucursal;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Repository
public class SucursalRepositoryImpl implements SucursalRepository {

    private final DynamoDbAsyncTable<Sucursal> table;

    public SucursalRepositoryImpl(DynamoDbAsyncClient dynamoDbAsyncClient) {
        DynamoDbEnhancedAsyncClient enhancedClient = DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
        this.table = enhancedClient.table("sucursal",
                TableSchema.fromBean(Sucursal.class));
    }

    @Override
    public Mono<Sucursal> save(Sucursal sucursal) {
        return Mono.fromFuture(table.putItem(sucursal)).thenReturn(sucursal);
    }

    @Override
    public Mono<Sucursal> update(String id, Sucursal sucursal) {
        return Mono.fromFuture(table.updateItem(sucursal)).thenReturn(sucursal);
    }

    @Override
    public Mono<Sucursal> findById(String id) {
        return Mono.fromFuture(table.getItem(
                r -> r.key(k -> k.partitionValue(id))));
    }

    @Override
    public Flux<Sucursal> findAll() {
        return Flux.from(table.scan().items());
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return Mono.fromFuture(table.deleteItem(
                r -> r.key(k -> k.partitionValue(id))))
                .then();
    }
}
