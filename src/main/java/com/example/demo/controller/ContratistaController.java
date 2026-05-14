package com.example.demo.controller;

import com.example.demo.dto.contratista.ContratistaRequest;
import com.example.demo.dto.contratista.ContratistaResponse;
import com.example.demo.model.Contratista;
import com.example.demo.service.ContratistaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratistas")
@RequiredArgsConstructor
public class ContratistaController {

    private final ContratistaService contratistaService;

    @GetMapping
    public ResponseEntity<Page<ContratistaResponse>> findAll(
            @PageableDefault(size = 20, sort = "idContratista") Pageable pageable) {
        return ResponseEntity.ok(contratistaService.findAll(pageable));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ContratistaResponse>> findByEstado(
            @PathVariable Contratista.EstadoContratista estado) {
        return ResponseEntity.ok(contratistaService.findByEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratistaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(contratistaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ContratistaResponse> create(@Valid @RequestBody ContratistaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratistaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratistaResponse> update(@PathVariable Integer id,
                                                       @Valid @RequestBody ContratistaRequest request) {
        return ResponseEntity.ok(contratistaService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ContratistaResponse> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam Contratista.EstadoContratista estado) {
        return ResponseEntity.ok(contratistaService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        contratistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
