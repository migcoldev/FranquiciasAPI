package com.pragma.franquicias.api.domain.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
public record Sucursal(String id, String nombre, String franquiciaId) {
    @DynamoDbPartitionKey
    public String id() {
        return id;
    }

    public String nombre() {
        return nombre;
    }

    public String franquiciaId() {
        return franquiciaId;
    }
}