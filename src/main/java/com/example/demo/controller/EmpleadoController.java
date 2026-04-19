package com.indecsa.controller;

import com.indecsa.dto.empleado.EmpleadoRequest;
import com.indecsa.dto.empleado.EmpleadoResponse;
import com.indecsa.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmpleadoResponse>> findAll() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpleadoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(empleadoService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpleadoResponse> create(@Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpleadoResponse> update(@PathVariable Integer id,
                                                    @Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.ok(empleadoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
