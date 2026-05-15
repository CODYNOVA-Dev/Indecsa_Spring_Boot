package com.example.demo.controller;

import com.example.demo.dto.request.RegistroMigratorioRequestDTO;
import com.example.demo.dto.response.RegistroMigratorioResponseDTO;
import com.example.demo.service.RegistroMigratorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registros-migratorios")
@RequiredArgsConstructor
public class RegistroMigratorioController {

    private final RegistroMigratorioService registroMigratorioService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<RegistroMigratorioResponseDTO>> getAll() {
        return ResponseEntity.ok(registroMigratorioService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<RegistroMigratorioResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(registroMigratorioService.findById(id));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<RegistroMigratorioResponseDTO> create(
            @Valid @RequestBody RegistroMigratorioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registroMigratorioService.create(dto));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<RegistroMigratorioResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody RegistroMigratorioRequestDTO dto) {
        return ResponseEntity.ok(registroMigratorioService.update(id, dto));
    }

    // ─── CAMBIAR ACTIVO ───────────────────────────────────────────────────────
    @PatchMapping("/{id}/activo")
    public ResponseEntity<RegistroMigratorioResponseDTO> cambiarActivo(
            @PathVariable Integer id,
            @RequestParam Boolean activo) {
        return ResponseEntity.ok(registroMigratorioService.cambiarActivo(id, activo));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        registroMigratorioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
