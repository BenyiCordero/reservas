package com.grupovo.reservas.service;

import com.grupovo.reservas.domain.Sala;
import com.grupovo.reservas.dto.SalaRequest;
import com.grupovo.reservas.dto.SalaResponse;
import com.grupovo.reservas.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaServiceImpl implements SalaService {

    private final SalaRepository salaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SalaResponse> listar() {
        return salaRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalaResponse> listarActivas() {
        return salaRepository.findByActivaTrueOrderByNombreAsc().stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SalaResponse obtenerPorId(Long id) {
        Sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sala no encontrada con id: " + id));
        return toResponse(sala);
    }

    @Override
    @Transactional
    public SalaResponse crear(SalaRequest request) {
        if (salaRepository.existsByNombreIgnoreCase(request.nombre())) {
            throw new RuntimeException("Ya existe una sala con el nombre: " + request.nombre());
        }
        Sala sala = Sala.builder()
            .nombre(request.nombre().trim())
            .activa(request.activa() == null || request.activa())
            .build();
        return toResponse(salaRepository.save(sala));
    }

    @Override
    @Transactional
    public SalaResponse actualizar(Long id, SalaRequest request) {
        Sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sala no encontrada con id: " + id));
        if (salaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre(), id)) {
            throw new RuntimeException("Ya existe otra sala con el nombre: " + request.nombre());
        }
        sala.setNombre(request.nombre().trim());
        if (request.activa() != null) {
            sala.setActiva(request.activa());
        }
        return toResponse(salaRepository.save(sala));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sala no encontrada con id: " + id));
        sala.setActiva(false);
        salaRepository.save(sala);
    }

    private SalaResponse toResponse(Sala sala) {
        return new SalaResponse(sala.getId(), sala.getNombre(), sala.isActiva());
    }
}
