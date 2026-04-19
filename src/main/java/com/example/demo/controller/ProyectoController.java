package com.indecsa.controller;

import com.indecsa.dto.proyecto.ProyectoRequest;
import com.indecsa.dto.proyecto.ProyectoResponse;
import com.indecsa.model.Proyecto;
import com.indecsa.service.ProyectoService;
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
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<Page<ProyectoResponse>> findByFiltros(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Proyecto.TipoProyecto tipo,
            @RequestParam(required = false) Proyecto.EstatusProyecto estatus,
            @RequestParam(required = false) Proyecto.EntidadFederativa estadoGeo,
            @RequestParam(required = false) String cliente,
            @PageableDefault(size = 20, sort = "nombreProyecto") Pageable pageable
    ) {
        return ResponseEntity.ok(
                proyectoService.findByFiltros(nombre, tipo, estatus, estadoGeo, cliente, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<ProyectoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(proyectoService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProyectoResponse> create(@Valid @RequestBody ProyectoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProyectoResponse> update(@PathVariable Integer id,
                                                    @Valid @RequestBody ProyectoRequest request) {
        return ResponseEntity.ok(proyectoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        proyectoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
