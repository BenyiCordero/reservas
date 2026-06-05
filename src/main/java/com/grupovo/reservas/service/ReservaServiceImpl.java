package com.grupovo.reservas.service;

import com.grupovo.reservas.domain.Area;
import com.grupovo.reservas.domain.Reserva;
import com.grupovo.reservas.domain.Sala;
import com.grupovo.reservas.dto.ReservaRequest;
import com.grupovo.reservas.dto.ReservaResponse;
import com.grupovo.reservas.repository.AreaRepository;
import com.grupovo.reservas.repository.ReservaRepository;
import com.grupovo.reservas.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;
    private final AreaRepository areaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponse> listarPorSalaYFecha(Long salaId, LocalDate fecha) {
        return reservaRepository.findBySalaIdAndFechaAndActivaTrueOrderByHoraInicioAsc(salaId, fecha)
            .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponse> listarPorSalaYSemana(Long salaId, LocalDate inicioSemana) {
        LocalDate finSemana = inicioSemana.plusDays(6);
        return reservaRepository
            .findBySalaIdAndFechaBetweenAndActivaTrueOrderByFechaAscHoraInicioAsc(salaId, inicioSemana, finSemana)
            .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponse obtenerPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
        return toResponse(reserva);
    }

    @Override
    @Transactional
    public ReservaResponse crear(ReservaRequest request) {
        Sala sala = salaRepository.findById(request.salaId())
            .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
        Area area = areaRepository.findById(request.areaId())
            .orElseThrow(() -> new RuntimeException("Area no encontrada"));

        validarDisponibilidad(request.salaId(), request.fecha(), request.horaInicio(), request.horaFin(), null);

        Reserva reserva = Reserva.builder()
            .sala(sala)
            .persona(request.persona().trim())
            .area(area)
            .fecha(request.fecha())
            .horaInicio(request.horaInicio())
            .horaFin(request.horaFin())
            .activa(request.activa() == null || request.activa())
            .build();
        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public ReservaResponse actualizar(Long id, ReservaRequest request) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        Area area = areaRepository.findById(request.areaId())
            .orElseThrow(() -> new RuntimeException("Area no encontrada"));

        if (!reserva.getFecha().equals(request.fecha()) ||
            !reserva.getHoraInicio().equals(request.horaInicio()) ||
            !reserva.getHoraFin().equals(request.horaFin())) {
            validarDisponibilidad(
                reserva.getSala().getId(), request.fecha(),
                request.horaInicio(), request.horaFin(), id);
        }

        reserva.setPersona(request.persona().trim());
        reserva.setArea(area);
        reserva.setFecha(request.fecha());
        reserva.setHoraInicio(request.horaInicio());
        reserva.setHoraFin(request.horaFin());
        if (request.activa() != null) {
            reserva.setActiva(request.activa());
        }
        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
        reserva.setActiva(false);
        reservaRepository.save(reserva);
    }

    private void validarDisponibilidad(Long salaId, LocalDate fecha,
                                       java.time.LocalTime horaInicio, java.time.LocalTime horaFin,
                                       Long excludeId) {
        List<Reserva> overlapping = reservaRepository.findOverlapping(salaId, fecha, horaInicio, horaFin);
        if (excludeId != null) {
            overlapping = overlapping.stream().filter(r -> !r.getId().equals(excludeId)).toList();
        }
        if (!overlapping.isEmpty()) {
            throw new RuntimeException("El horario seleccionado se superpone con otra reserva existente");
        }
    }

    private ReservaResponse toResponse(Reserva r) {
        return new ReservaResponse(
            r.getId(), r.getSala().getId(), r.getSala().getNombre(),
            r.getPersona(), r.getArea().getId(), r.getArea().getNombre(),
            r.getFecha(), r.getHoraInicio(), r.getHoraFin(), r.isActiva());
    }
}
