package com.pragma.franquicias.api.infrastructure.adapter;

import com.pragma.franquicias.api.domain.model.Producto;
import com.pragma.franquicias.api.domain.model.ProductoConSucursal;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductoDynamoDbAdapter {

    private final DynamoDbAsyncTable<Producto> table;
    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    public ProductoDynamoDbAdapter(DynamoDbAsyncClient dynamoDbAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
        DynamoDbEnhancedAsyncClient enhancedClient = DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
        this.table = enhancedClient.table("productos",
                TableSchema.fromBean(Producto.class));
    }

    public Mono<Producto> save(Producto producto) {
        return Mono.fromFuture(table.putItem(producto)).thenReturn(producto);
    }

    public Mono<Producto> update(String id, Producto producto) {
        return Mono.fromFuture(table.updateItem(producto)).thenReturn(producto);
    }

    public Mono<Producto> findById(String id) {
        return Mono.fromFuture(table.getItem(
                r -> r.key(k -> k.partitionValue(id))));
    }

    public Flux<Producto> findAll() {
        return Flux.from(table.scan().items());
    }

    public Mono<Void> deleteById(String id) {
        return Mono.fromFuture(table.deleteItem(
                        r -> r.key(k -> k.partitionValue(id))))
                .then();
    }

    public Mono<Producto> updateStock(String id, int stock) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(id).build());

        Map<String, String> expressionAttributeNames = Map.of("#s", "stock");
        Map<String, AttributeValue> expressionAttributeValues = Map.of(
                ":stock", AttributeValue.builder().n(String.valueOf(stock)).build()
        );

        UpdateItemRequest updateRequest = UpdateItemRequest.builder()
                .tableName("productos")
                .key(key)
                .updateExpression("SET #s = :stock")
                .expressionAttributeNames(expressionAttributeNames)
                .expressionAttributeValues(expressionAttributeValues)
                .returnValues(ReturnValue.ALL_NEW)
                .build();

        return Mono.fromFuture(() -> dynamoDbAsyncClient.updateItem(updateRequest))
                .flatMap(response -> findById(id));
    }

    public Flux<ProductoConSucursal> getMaxStockBySucursal() {
        ScanRequest scanProductosRequest = ScanRequest.builder()
                .tableName("productos")
                .build();

        return Mono.fromFuture(() -> dynamoDbAsyncClient.scan(scanProductosRequest))
                .flatMapMany(productosResponse -> {
                    Map<String, ProductoConSucursal> maxStockBySucursal = new HashMap<>();

                    for (Map<String, AttributeValue> productoItem : productosResponse.items()) {
                        String sucursalId = productoItem.get("sucursalId").s();
                        String productoNombre = productoItem.get("nombre").s();
                        int stock = Integer.parseInt(productoItem.get("stock").n());

                        ProductoConSucursal current = maxStockBySucursal.get(sucursalId);
                        if (current == null || stock > current.stock()) {
                            GetItemRequest getSucursalRequest = GetItemRequest.builder()
                                    .tableName("sucursales")
                                    .key(Map.of("id", AttributeValue.builder().s(sucursalId).build()))
                                    .build();

                            String sucursalNombre = Mono.fromFuture(() -> dynamoDbAsyncClient.getItem(getSucursalRequest))
                                    .map(getItemResponse -> getItemResponse.item().get("nombre").s())
                                    .block();

                            maxStockBySucursal.put(sucursalId, new ProductoConSucursal(
                                    productoItem.get("id").s(),
                                    productoNombre,
                                    sucursalId,
                                    sucursalNombre,
                                    stock
                            ));
                        }
                    }

                    return Flux.fromIterable(maxStockBySucursal.values());
                });
    }
}