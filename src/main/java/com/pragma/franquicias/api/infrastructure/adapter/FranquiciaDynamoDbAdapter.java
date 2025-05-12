package com.pragma.franquicias.api.infrastructure.adapter;

import com.pragma.franquicias.api.domain.model.Franquicia;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Component
public class FranquiciaDynamoDbAdapter {

    private final DynamoDbAsyncTable<Franquicia> table;

    public FranquiciaDynamoDbAdapter(DynamoDbAsyncClient dynamoDbAsyncClient) {
        DynamoDbEnhancedAsyncClient enhancedClient = DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
        this.table = enhancedClient.table("franquicias",
                TableSchema.fromBean(Franquicia.class));
    }

    public Mono<Franquicia> save(Franquicia franquicia) {
        return Mono.fromFuture(table.putItem(franquicia)).thenReturn(franquicia);
    }

    public Mono<Franquicia> update(String id, Franquicia franquicia) {
        return Mono.fromFuture(table.updateItem(franquicia)).thenReturn(franquicia);
    }

    public Mono<Franquicia> findById(String id) {
        return Mono.fromFuture(table.getItem(
                r -> r.key(k -> k.partitionValue(id))));
    }

    public Flux<Franquicia> findAll() {
        return Flux.from(table.scan().items());
    }

    public Mono<Void> deleteById(String id) {
        return Mono.fromFuture(table.deleteItem(
                        r -> r.key(k -> k.partitionValue(id))))
                .then();
    }
}