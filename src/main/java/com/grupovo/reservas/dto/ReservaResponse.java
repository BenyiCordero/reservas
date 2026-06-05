package com.grupovo.reservas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaResponse(
    Long id,
    Long salaId,
    String salaNombre,
    String persona,
    Long areaId,
    String areaNombre,
    LocalDate fecha,
    LocalTime horaInicio,
    LocalTime horaFin,
    boolean activa
) {}
