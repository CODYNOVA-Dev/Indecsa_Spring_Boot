package com.example.demo.controller;

import com.example.demo.dto.request.EstandarRendimientoRequestDTO;
import com.example.demo.dto.response.EstandarRendimientoResponseDTO;
import com.example.demo.model.EstandarRendimiento.UnidadMedida;
import com.example.demo.service.EstandarRendimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estandares-rendimiento")
@RequiredArgsConstructor
public class EstandarRendimientoController {

    private final EstandarRendimientoService estandarService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<EstandarRendimientoResponseDTO>> getAll() {
        return ResponseEntity.ok(estandarService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<EstandarRendimientoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(estandarService.findById(id));
    }

    // ─── GET BY NOMBRE ACTIVIDAD ──────────────────────────────────────────────
    @GetMapping("/buscar")
    public ResponseEntity<List<EstandarRendimientoResponseDTO>> getByNombre(
            @RequestParam String nombre) {
        return ResponseEntity.ok(estandarService.findByNombreActividad(nombre));
    }

    // ─── GET BY UNIDAD MEDIDA ─────────────────────────────────────────────────
    @GetMapping("/unidad/{unidadMedida}")
    public ResponseEntity<List<EstandarRendimientoResponseDTO>> getByUnidadMedida(
            @PathVariable UnidadMedida unidadMedida) {
        return ResponseEntity.ok(estandarService.findByUnidadMedida(unidadMedida));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<EstandarRendimientoResponseDTO> create(
            @Valid @RequestBody EstandarRendimientoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estandarService.create(dto));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<EstandarRendimientoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EstandarRendimientoRequestDTO dto) {
        return ResponseEntity.ok(estandarService.update(id, dto));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        estandarService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
