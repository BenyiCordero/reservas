package com.grupovo.reservas.controller;

import com.grupovo.reservas.dto.SalaRequest;
import com.grupovo.reservas.dto.SalaResponse;
import com.grupovo.reservas.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaResponse>> listar() {
        return ResponseEntity.ok(salaService.listar());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<SalaResponse>> listarActivas() {
        return ResponseEntity.ok(salaService.listarActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SalaResponse> crear(@Valid @RequestBody SalaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody SalaRequest request) {
        return ResponseEntity.ok(salaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        salaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
