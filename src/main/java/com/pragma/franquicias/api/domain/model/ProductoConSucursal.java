package com.pragma.franquicias.api.domain.model;

import java.util.List;

public record ProductoConSucursal(String id, String nombre, String sucursalId, String sucursal, int stock) {}