package com.example.demo.controller;

import com.example.demo.dto.request.ContratistaRequestDTO;
import com.example.demo.dto.response.ContratistaResponseDTO;
import com.example.demo.model.Contratista.EstadoContratista;
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
    public ResponseEntity<Page<ContratistaResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "idContratista") Pageable pageable) {
        return ResponseEntity.ok(contratistaService.findAll(pageable));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ContratistaResponseDTO>> findByEstado(
            @PathVariable EstadoContratista estado) {
        return ResponseEntity.ok(contratistaService.findByEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratistaResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(contratistaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ContratistaResponseDTO> create(@Valid @RequestBody ContratistaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratistaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratistaResponseDTO> update(@PathVariable Integer id,
                                                          @Valid @RequestBody ContratistaRequestDTO request) {
        return ResponseEntity.ok(contratistaService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ContratistaResponseDTO> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoContratista estado) {
        return ResponseEntity.ok(contratistaService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        contratistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
