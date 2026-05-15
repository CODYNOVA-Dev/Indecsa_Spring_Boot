package com.example.demo.controller;

import com.example.demo.dto.request.EstadoRequestDTO;
import com.example.demo.dto.response.EstadoResponseDTO;
import com.example.demo.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<EstadoResponseDTO>> getAll() {
        return ResponseEntity.ok(estadoService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<EstadoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(estadoService.findById(id));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<EstadoResponseDTO> create(@Valid @RequestBody EstadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.create(dto));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<EstadoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EstadoRequestDTO dto) {
        return ResponseEntity.ok(estadoService.update(id, dto));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        estadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
