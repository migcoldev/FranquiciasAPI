package com.pragma.franquicias.api.domain.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public record Producto(String id, String nombre, String sucursalId, int stock) {
    @DynamoDbPartitionKey
    public String id() {
        return id;
    }

    @DynamoDbSortKey
    public String sucursalId() {
        return sucursalId;
    }

    public String nombre() {
        return nombre;
    }

    public int stock() {
        return stock;
    }
}