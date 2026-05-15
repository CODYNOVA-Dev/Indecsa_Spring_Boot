package com.example.demo.controller;

import com.example.demo.dto.request.DomicilioRequestDTO;
import com.example.demo.dto.response.DomicilioResponseDTO;
import com.example.demo.service.DomicilioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/domicilios")
@RequiredArgsConstructor
public class DomicilioController {

    private final DomicilioService domicilioService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<DomicilioResponseDTO>> getAll() {
        return ResponseEntity.ok(domicilioService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<DomicilioResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(domicilioService.findById(id));
    }

    // ─── GET BY ESTADO ────────────────────────────────────────────────────────
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<DomicilioResponseDTO>> getByEstado(@PathVariable Integer idEstado) {
        return ResponseEntity.ok(domicilioService.findByEstado(idEstado));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<DomicilioResponseDTO> create(@Valid @RequestBody DomicilioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(domicilioService.create(dto));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<DomicilioResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody DomicilioRequestDTO dto) {
        return ResponseEntity.ok(domicilioService.update(id, dto));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        domicilioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
