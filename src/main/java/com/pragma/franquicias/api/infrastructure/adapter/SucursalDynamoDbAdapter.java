package com.pragma.franquicias.api.infrastructure.adapter;

import com.pragma.franquicias.api.domain.model.Sucursal;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Component
public class SucursalDynamoDbAdapter {

    private final DynamoDbAsyncTable<Sucursal> table;

    public SucursalDynamoDbAdapter(DynamoDbAsyncClient dynamoDbAsyncClient) {
        DynamoDbEnhancedAsyncClient enhancedClient = DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
        this.table = enhancedClient.table("sucursales",
                TableSchema.fromBean(Sucursal.class));
    }

    public Mono<Sucursal> save(Sucursal sucursal) {
        return Mono.fromFuture(table.putItem(sucursal)).thenReturn(sucursal);
    }

    public Mono<Sucursal> update(String id, Sucursal sucursal) {
        return Mono.fromFuture(table.updateItem(sucursal)).thenReturn(sucursal);
    }

    public Mono<Sucursal> findById(String id) {
        return Mono.fromFuture(table.getItem(
                r -> r.key(k -> k.partitionValue(id))));
    }

    public Flux<Sucursal> findAll() {
        return Flux.from(table.scan().items());
    }

    public Mono<Void> deleteById(String id) {
        return Mono.fromFuture(table.deleteItem(
                        r -> r.key(k -> k.partitionValue(id))))
                .then();
    }
}