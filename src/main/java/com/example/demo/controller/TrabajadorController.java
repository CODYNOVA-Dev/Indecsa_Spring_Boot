package com.indecsa.controller;

import com.indecsa.dto.trabajador.TrabajadorRequest;
import com.indecsa.dto.trabajador.TrabajadorResponse;
import com.indecsa.model.Trabajador;
import com.indecsa.service.TrabajadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<Page<TrabajadorResponse>> findByFiltros(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Trabajador.EstadoTrabajador estado,
            @RequestParam(required = false) String especialidad,
            @RequestParam(required = false) String puesto,
            @RequestParam(required = false) Trabajador.EntidadFederativa calidadVida,
            @PageableDefault(size = 20, sort = "nombreTrabajador") Pageable pageable
    ) {
        return ResponseEntity.ok(
                trabajadorService.findByFiltros(nombre, estado, especialidad, puesto, calidadVida, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<TrabajadorResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(trabajadorService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<TrabajadorResponse> create(@Valid @RequestBody TrabajadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trabajadorService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<TrabajadorResponse> update(@PathVariable Integer id,
                                                      @Valid @RequestBody TrabajadorRequest request) {
        return ResponseEntity.ok(trabajadorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        trabajadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
