package com.example.demo.controller;

import com.example.demo.dto.response.EstadoResponseDTO;
import com.example.demo.service.EstadoService;
import lombok.RequiredArgsConstructor;
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
}
