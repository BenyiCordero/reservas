package com.grupovo.reservas.service;

import com.grupovo.reservas.dto.SalaRequest;
import com.grupovo.reservas.dto.SalaResponse;

import java.util.List;

public interface SalaService {
    List<SalaResponse> listar();
    List<SalaResponse> listarActivas();
    SalaResponse obtenerPorId(Long id);
    SalaResponse crear(SalaRequest request);
    SalaResponse actualizar(Long id, SalaRequest request);
    void eliminar(Long id);
}
