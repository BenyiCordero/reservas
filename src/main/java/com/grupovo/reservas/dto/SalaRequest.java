package com.grupovo.reservas.dto;

import jakarta.validation.constraints.NotBlank;

public record SalaRequest(
    @NotBlank String nombre,
    Boolean activa
) {}
