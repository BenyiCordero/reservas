package com.grupovo.reservas.controller;

import com.grupovo.reservas.dto.ReservaRequest;
import com.grupovo.reservas.dto.ReservaResponse;
import com.grupovo.reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping("/semana")
    public ResponseEntity<List<ReservaResponse>> listarPorSemana(
            @RequestParam Long salaId,
            @RequestParam LocalDate inicio) {
        return ResponseEntity.ok(reservaService.listarPorSalaYSemana(salaId, inicio));
    }

    @GetMapping("/dia")
    public ResponseEntity<List<ReservaResponse>> listarPorDia(
            @RequestParam Long salaId,
            @RequestParam LocalDate fecha) {
        return ResponseEntity.ok(reservaService.listarPorSalaYFecha(salaId, fecha));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> crear(@Valid @RequestBody ReservaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ReservaRequest request) {
        return ResponseEntity.ok(reservaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
