package com.grupovo.reservas.repository;

import com.grupovo.reservas.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findBySalaIdAndFechaAndActivaTrueOrderByHoraInicioAsc(Long salaId, LocalDate fecha);

    List<Reserva> findBySalaIdAndFechaBetweenAndActivaTrueOrderByFechaAscHoraInicioAsc(
        Long salaId, LocalDate inicio, LocalDate fin);

    @Query("SELECT r FROM Reserva r WHERE r.sala.id = :salaId AND r.fecha = :fecha AND r.activa = true " +
           "AND r.horaInicio < :horaFin AND r.horaFin > :horaInicio")
    List<Reserva> findOverlapping(
        @Param("salaId") Long salaId,
        @Param("fecha") LocalDate fecha,
        @Param("horaInicio") LocalTime horaInicio,
        @Param("horaFin") LocalTime horaFin);
}
