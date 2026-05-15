package com.example.demo.controller;

import com.example.demo.dto.request.AsignacionTrabajadorProyectoRequestDTO;
import com.example.demo.dto.response.AsignacionTrabajadorProyectoResponseDTO;
import com.example.demo.service.AsignacionTrabajadorProyectoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones-trabajador")
@RequiredArgsConstructor
public class AsignacionTrabajadorProyectoController {

    private final AsignacionTrabajadorProyectoService asignacionTpService;

    @GetMapping("/proyecto/{idProyecto}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<AsignacionTrabajadorProyectoResponseDTO>> findByProyecto(
            @PathVariable Integer idProyecto) {
        return ResponseEntity.ok(asignacionTpService.findByProyecto(idProyecto));
    }

    @GetMapping("/trabajador/{idTrabajador}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<AsignacionTrabajadorProyectoResponseDTO>> findByTrabajador(
            @PathVariable Integer idTrabajador) {
        return ResponseEntity.ok(asignacionTpService.findByTrabajador(idTrabajador));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AsignacionTrabajadorProyectoResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(asignacionTpService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AsignacionTrabajadorProyectoResponseDTO> create(
            @Valid @RequestBody AsignacionTrabajadorProyectoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignacionTpService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AsignacionTrabajadorProyectoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AsignacionTrabajadorProyectoRequestDTO request) {
        return ResponseEntity.ok(asignacionTpService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        asignacionTpService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
