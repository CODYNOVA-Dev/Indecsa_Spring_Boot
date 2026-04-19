package com.indecsa.controller;

import com.indecsa.dto.asignacion.AsignacionTrabajadorProyectoRequest;
import com.indecsa.dto.asignacion.AsignacionTrabajadorProyectoResponse;
import com.indecsa.service.AsignacionTrabajadorProyectoService;
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
    public ResponseEntity<List<AsignacionTrabajadorProyectoResponse>> findByProyecto(
            @PathVariable Integer idProyecto) {
        return ResponseEntity.ok(asignacionTpService.findByProyecto(idProyecto));
    }

    @GetMapping("/trabajador/{idTrabajador}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<AsignacionTrabajadorProyectoResponse>> findByTrabajador(
            @PathVariable Integer idTrabajador) {
        return ResponseEntity.ok(asignacionTpService.findByTrabajador(idTrabajador));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AsignacionTrabajadorProyectoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(asignacionTpService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AsignacionTrabajadorProyectoResponse> create(
            @Valid @RequestBody AsignacionTrabajadorProyectoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignacionTpService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AsignacionTrabajadorProyectoResponse> update(
            @PathVariable Integer id,
            @Valid @RequestBody AsignacionTrabajadorProyectoRequest request) {
        return ResponseEntity.ok(asignacionTpService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        asignacionTpService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
