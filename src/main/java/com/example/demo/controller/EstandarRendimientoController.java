package com.example.demo.controller;

import com.example.demo.dto.request.EstandarRendimientoRequestDTO;
import com.example.demo.dto.response.EstandarRendimientoResponseDTO;
import com.example.demo.service.EstandarRendimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estandares-rendimiento")
@RequiredArgsConstructor
public class EstandarRendimientoController {

    private final EstandarRendimientoService estandarService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<EstandarRendimientoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(estandarService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<EstandarRendimientoResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(estandarService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstandarRendimientoResponseDTO> crear(@Valid @RequestBody EstandarRendimientoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estandarService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstandarRendimientoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EstandarRendimientoRequestDTO request) {
        return ResponseEntity.ok(estandarService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        estandarService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
