package com.example.demo.controller;

import com.example.demo.dto.request.RegistroHorasRequestDTO;
import com.example.demo.dto.response.RegistroHorasResponseDTO;
import com.example.demo.service.RegistroHorasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/registros-horas")
@RequiredArgsConstructor
public class RegistroHorasController {

    private final RegistroHorasService registroHorasService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<RegistroHorasResponseDTO>> getAll() {
        return ResponseEntity.ok(registroHorasService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<RegistroHorasResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(registroHorasService.findById(id));
    }

    // ─── GET BY ASIGNACION ────────────────────────────────────────────────────
    @GetMapping("/asignacion/{idAsignacionTp}")
    public ResponseEntity<List<RegistroHorasResponseDTO>> getByAsignacion(
            @PathVariable Integer idAsignacionTp) {
        return ResponseEntity.ok(registroHorasService.findByAsignacion(idAsignacionTp));
    }

    // ─── GET BY CUADRILLA ─────────────────────────────────────────────────────
    @GetMapping("/cuadrilla/{idCuadrilla}")
    public ResponseEntity<List<RegistroHorasResponseDTO>> getByCuadrilla(
            @PathVariable Integer idCuadrilla) {
        return ResponseEntity.ok(registroHorasService.findByCuadrilla(idCuadrilla));
    }

    // ─── GET BY FECHA ─────────────────────────────────────────────────────────
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<RegistroHorasResponseDTO>> getByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(registroHorasService.findByFecha(fecha));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<RegistroHorasResponseDTO> create(
            @Valid @RequestBody RegistroHorasRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroHorasService.create(dto));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<RegistroHorasResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody RegistroHorasRequestDTO dto) {
        return ResponseEntity.ok(registroHorasService.update(id, dto));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        registroHorasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
