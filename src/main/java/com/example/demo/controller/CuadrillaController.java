package com.example.demo.controller;

import com.example.demo.dto.request.CuadrillaRequestDTO;
import com.example.demo.dto.response.CuadrillaResponseDTO;
import com.example.demo.model.Cuadrilla.EstatusCuadrilla;
import com.example.demo.service.CuadrillaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cuadrillas")
@RequiredArgsConstructor
public class CuadrillaController {

    private final CuadrillaService cuadrillaService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<CuadrillaResponseDTO>> getAll() {
        return ResponseEntity.ok(cuadrillaService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<CuadrillaResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cuadrillaService.findById(id));
    }

    // ─── GET BY PROYECTO ──────────────────────────────────────────────────────
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<CuadrillaResponseDTO>> getByProyecto(
            @PathVariable Integer idProyecto) {
        return ResponseEntity.ok(cuadrillaService.findByProyecto(idProyecto));
    }

    // ─── GET BY ESTATUS ───────────────────────────────────────────────────────
    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<CuadrillaResponseDTO>> getByEstatus(
            @PathVariable EstatusCuadrilla estatus) {
        return ResponseEntity.ok(cuadrillaService.findByEstatus(estatus));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<CuadrillaResponseDTO> create(@Valid @RequestBody CuadrillaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuadrillaService.create(dto));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<CuadrillaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody CuadrillaRequestDTO dto) {
        return ResponseEntity.ok(cuadrillaService.update(id, dto));
    }

    // ─── CAMBIAR ESTATUS ──────────────────────────────────────────────────────
    @PatchMapping("/{id}/estatus")
    public ResponseEntity<CuadrillaResponseDTO> cambiarEstatus(
            @PathVariable Integer id,
            @RequestParam EstatusCuadrilla estatus) {
        return ResponseEntity.ok(cuadrillaService.cambiarEstatus(id, estatus));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cuadrillaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
