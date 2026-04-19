package com.indecsa.controller;

import com.indecsa.dto.asignacion.AsignacionProyectoContratistaRequest;
import com.indecsa.dto.asignacion.AsignacionProyectoContratistaResponse;
import com.indecsa.service.AsignacionProyectoContratistaService;
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
    public ResponseEntity<List<AsignacionProyectoContratistaResponse>> findByProyecto(
            @PathVariable Integer idProyecto) {
        return ResponseEntity.ok(asignacionPcService.findByProyecto(idProyecto));
    }

    @GetMapping("/contratista/{idContratista}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<AsignacionProyectoContratistaResponse>> findByContratista(
            @PathVariable Integer idContratista) {
        return ResponseEntity.ok(asignacionPcService.findByContratista(idContratista));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AsignacionProyectoContratistaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(asignacionPcService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsignacionProyectoContratistaResponse> create(
            @Valid @RequestBody AsignacionProyectoContratistaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignacionPcService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsignacionProyectoContratistaResponse> update(
            @PathVariable Integer id,
            @Valid @RequestBody AsignacionProyectoContratistaRequest request) {
        return ResponseEntity.ok(asignacionPcService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        asignacionPcService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
