package com.example.demo.controller;

import com.example.demo.dto.request.AvancePartidaRequestDTO;
import com.example.demo.dto.response.AvancePartidaResponseDTO;
import com.example.demo.service.AvancePartidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/avances-partida")
@RequiredArgsConstructor
public class AvancePartidaController {

    private final AvancePartidaService avancePartidaService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<AvancePartidaResponseDTO>> getAll() {
        return ResponseEntity.ok(avancePartidaService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<AvancePartidaResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(avancePartidaService.findById(id));
    }

    // ─── GET BY PROYECTO ──────────────────────────────────────────────────────
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<AvancePartidaResponseDTO>> getByProyecto(
            @PathVariable Integer idProyecto) {
        return ResponseEntity.ok(avancePartidaService.findByProyecto(idProyecto));
    }

    // ─── GET BY CUADRILLA ─────────────────────────────────────────────────────
    @GetMapping("/cuadrilla/{idCuadrilla}")
    public ResponseEntity<List<AvancePartidaResponseDTO>> getByCuadrilla(
            @PathVariable Integer idCuadrilla) {
        return ResponseEntity.ok(avancePartidaService.findByCuadrilla(idCuadrilla));
    }

    // ─── GET BY FECHA ─────────────────────────────────────────────────────────
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<AvancePartidaResponseDTO>> getByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(avancePartidaService.findByFecha(fecha));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<AvancePartidaResponseDTO> create(
            @Valid @RequestBody AvancePartidaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(avancePartidaService.create(dto));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<AvancePartidaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AvancePartidaRequestDTO dto) {
        return ResponseEntity.ok(avancePartidaService.update(id, dto));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        avancePartidaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
