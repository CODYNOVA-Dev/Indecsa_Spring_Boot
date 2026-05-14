package com.example.demo.controller;

import com.example.demo.dto.request.AsignacionProyectoContratistaRequestDTO;
import com.example.demo.dto.response.AsignacionProyectoContratistaResponseDTO;
import com.example.demo.service.AsignacionProyectoContratistaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones-contratista")
@RequiredArgsConstructor
public class AsignacionProyectoContratistaController {

    private final AsignacionProyectoContratistaService asignacionPcService;

    @GetMapping("/proyecto/{idProyecto}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<AsignacionProyectoContratistaResponseDTO>> findByProyecto(
            @PathVariable Integer idProyecto) {
        return ResponseEntity.ok(asignacionPcService.findByProyecto(idProyecto));
    }

    @GetMapping("/contratista/{idContratista}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<AsignacionProyectoContratistaResponseDTO>> findByContratista(
            @PathVariable Integer idContratista) {
        return ResponseEntity.ok(asignacionPcService.findByContratista(idContratista));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AsignacionProyectoContratistaResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(asignacionPcService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsignacionProyectoContratistaResponseDTO> create(
            @Valid @RequestBody AsignacionProyectoContratistaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignacionPcService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsignacionProyectoContratistaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AsignacionProyectoContratistaRequestDTO request) {
        return ResponseEntity.ok(asignacionPcService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        asignacionPcService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
