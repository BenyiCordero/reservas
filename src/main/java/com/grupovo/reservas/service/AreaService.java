package com.grupovo.reservas.service;

import com.grupovo.reservas.dto.AreaRequest;
import com.grupovo.reservas.dto.AreaResponse;

import java.util.List;

public interface AreaService {
    List<AreaResponse> listar();
    List<AreaResponse> listarActivas();
    AreaResponse obtenerPorId(Long id);
    AreaResponse crear(AreaRequest request);
    AreaResponse actualizar(Long id, AreaRequest request);
    void eliminar(Long id);
}
