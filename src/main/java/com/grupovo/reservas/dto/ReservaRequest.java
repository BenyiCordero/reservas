package com.grupovo.reservas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaRequest(
    @NotNull Long salaId,
    @NotBlank String persona,
    @NotNull Long areaId,
    @NotNull LocalDate fecha,
    @NotNull LocalTime horaInicio,
    @NotNull LocalTime horaFin,
    Boolean activa
) {}
