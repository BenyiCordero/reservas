package com.grupovo.reservas.controller;

import com.grupovo.reservas.dto.AreaRequest;
import com.grupovo.reservas.dto.AreaResponse;
import com.grupovo.reservas.service.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping
    public ResponseEntity<List<AreaResponse>> listar() {
        return ResponseEntity.ok(areaService.listar());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<AreaResponse>> listarActivas() {
        return ResponseEntity.ok(areaService.listarActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<AreaResponse> crear(@Valid @RequestBody AreaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(areaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody AreaRequest request) {
        return ResponseEntity.ok(areaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        areaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
