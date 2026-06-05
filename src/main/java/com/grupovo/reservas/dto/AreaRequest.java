package com.grupovo.reservas.dto;

import jakarta.validation.constraints.NotBlank;

public record AreaRequest(
    @NotBlank String nombre,
    Boolean activa
) {}
