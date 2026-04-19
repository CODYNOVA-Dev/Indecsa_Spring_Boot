package com.indecsa.controller;

import com.indecsa.dto.contratista.ContratistaRequest;
import com.indecsa.dto.contratista.ContratistaResponse;
import com.indecsa.model.Contratista;
import com.indecsa.service.ContratistaService;
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
@RequestMapping("/api/contratistas")
@RequiredArgsConstructor
public class ContratistaController {

    private final ContratistaService contratistaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<Page<ContratistaResponse>> findByFiltros(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Contratista.EstadoContratista estado,
            @RequestParam(required = false) Contratista.EntidadFederativa ubicacion,
            @RequestParam(required = false) Byte calificacionMin,
            @PageableDefault(size = 20, sort = "nombreContratista") Pageable pageable
    ) {
        return ResponseEntity.ok(
                contratistaService.findByFiltros(nombre, estado, ubicacion, calificacionMin, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<ContratistaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(contratistaService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContratistaResponse> create(@Valid @RequestBody ContratistaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratistaService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContratistaResponse> update(@PathVariable Integer id,
                                                       @Valid @RequestBody ContratistaRequest request) {
        return ResponseEntity.ok(contratistaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        contratistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
