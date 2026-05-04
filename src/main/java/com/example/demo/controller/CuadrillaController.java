package com.example.demo.controller;

import com.example.demo.dto.cuadrilla.CuadrillaRequest;
import com.example.demo.dto.cuadrilla.CuadrillaResponse;
import com.example.demo.service.CuadrillaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuadrillas")
@RequiredArgsConstructor
public class CuadrillaController {

    private final CuadrillaService cuadrillaService;

    @GetMapping("/proyecto/{idProyecto}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<CuadrillaResponse>> listarPorProyecto(@PathVariable Integer idProyecto) {
        return ResponseEntity.ok(cuadrillaService.listarPorProyecto(idProyecto));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CuadrillaResponse> crear(@RequestBody CuadrillaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuadrillaService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CuadrillaResponse> actualizar(
            @PathVariable Integer id,
            @RequestBody CuadrillaRequest request) {
        return ResponseEntity.ok(cuadrillaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        cuadrillaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
