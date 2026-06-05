package com.grupovo.reservas.service;

import com.grupovo.reservas.domain.Area;
import com.grupovo.reservas.dto.AreaRequest;
import com.grupovo.reservas.dto.AreaResponse;
import com.grupovo.reservas.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AreaResponse> listar() {
        return areaRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaResponse> listarActivas() {
        return areaRepository.findByActivaTrueOrderByNombreAsc().stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AreaResponse obtenerPorId(Long id) {
        Area area = areaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Area no encontrada con id: " + id));
        return toResponse(area);
    }

    @Override
    @Transactional
    public AreaResponse crear(AreaRequest request) {
        if (areaRepository.existsByNombreIgnoreCase(request.nombre())) {
            throw new RuntimeException("Ya existe un área con el nombre: " + request.nombre());
        }
        Area area = Area.builder()
            .nombre(request.nombre().trim())
            .activa(request.activa() == null || request.activa())
            .build();
        return toResponse(areaRepository.save(area));
    }

    @Override
    @Transactional
    public AreaResponse actualizar(Long id, AreaRequest request) {
        Area area = areaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Area no encontrada con id: " + id));
        if (areaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre(), id)) {
            throw new RuntimeException("Ya existe otra área con el nombre: " + request.nombre());
        }
        area.setNombre(request.nombre().trim());
        if (request.activa() != null) {
            area.setActiva(request.activa());
        }
        return toResponse(areaRepository.save(area));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Area area = areaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Area no encontrada con id: " + id));
        area.setActiva(false);
        areaRepository.save(area);
    }

    private AreaResponse toResponse(Area area) {
        return new AreaResponse(area.getId(), area.getNombre(), area.isActiva());
    }
}
