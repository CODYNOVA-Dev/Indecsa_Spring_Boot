package com.indecsa.controller;

import com.indecsa.dto.ubicacion.UbicacionProyectoRequest;
import com.indecsa.dto.ubicacion.UbicacionProyectoResponse;
import com.indecsa.model.UbicacionProyecto;
import com.indecsa.service.UbicacionProyectoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
@RequiredArgsConstructor
public class UbicacionProyectoController {

    private final UbicacionProyectoService ubicacionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<UbicacionProyectoResponse>> findAll(
            @RequestParam(required = false) UbicacionProyecto.EntidadFederativa estado) {
        if (estado != null) {
            return ResponseEntity.ok(ubicacionService.findByEstado(estado));
        }
        return ResponseEntity.ok(ubicacionService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<UbicacionProyectoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ubicacionService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UbicacionProyectoResponse> create(
            @Valid @RequestBody UbicacionProyectoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ubicacionService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UbicacionProyectoResponse> update(@PathVariable Integer id,
                                                             @Valid @RequestBody UbicacionProyectoRequest request) {
        return ResponseEntity.ok(ubicacionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ubicacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
