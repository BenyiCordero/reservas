package com.grupovo.reservas.service;

import com.grupovo.reservas.dto.ReservaRequest;
import com.grupovo.reservas.dto.ReservaResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReservaService {
    List<ReservaResponse> listarPorSalaYFecha(Long salaId, LocalDate fecha);
    List<ReservaResponse> listarPorSalaYSemana(Long salaId, LocalDate inicioSemana);
    ReservaResponse obtenerPorId(Long id);
    ReservaResponse crear(ReservaRequest request);
    ReservaResponse actualizar(Long id, ReservaRequest request);
    void cancelar(Long id);
}
