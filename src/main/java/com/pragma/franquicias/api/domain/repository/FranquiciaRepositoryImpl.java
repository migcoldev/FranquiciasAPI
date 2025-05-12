package com.pragma.franquicias.api.domain.repository;

import com.pragma.franquicias.api.domain.model.Franquicia;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Repository
public class FranquiciaRepositoryImpl implements FranquiciaRepository {

    private final DynamoDbAsyncTable<Franquicia> table;

    public FranquiciaRepositoryImpl(DynamoDbAsyncClient dynamoDbAsyncClient) {
        DynamoDbEnhancedAsyncClient enhancedClient = DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
        this.table = enhancedClient.table("franquicia",
                TableSchema.fromBean(Franquicia.class));
    }

    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        return Mono.fromFuture(table.putItem(franquicia)).thenReturn(franquicia);
    }

    @Override
    public Mono<Franquicia> update(String id, Franquicia franquicia) {
        return Mono.fromFuture(table.updateItem(franquicia)).thenReturn(franquicia);
    }

    @Override
    public Mono<Franquicia> findById(String id) {
        return Mono.fromFuture(table.getItem(
                r -> r.key(k -> k.partitionValue(id))));
    }

    @Override
    public Flux<Franquicia> findAll() {
        return Flux.from(table.scan().items());
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return Mono.fromFuture(table.deleteItem(
                r -> r.key(k -> k.partitionValue(id))))
                .then();
    }
}
