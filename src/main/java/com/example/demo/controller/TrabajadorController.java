package com.example.demo.controller;

import com.example.demo.dto.request.TrabajadorRequestDTO;
import com.example.demo.dto.response.TrabajadorResponseDTO;
import com.example.demo.model.Trabajador;
import com.example.demo.service.TrabajadorService;
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
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    @GetMapping
    public ResponseEntity<Page<TrabajadorResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "idTrabajador") Pageable pageable) {
        return ResponseEntity.ok(trabajadorService.findAll(pageable));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TrabajadorResponseDTO>> findByEstado(
            @PathVariable Trabajador.EstadoTrabajador estado) {
        return ResponseEntity.ok(trabajadorService.findByEstado(estado));
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<TrabajadorResponseDTO>> findByEspecialidad(
            @PathVariable String especialidad) {
        return ResponseEntity.ok(trabajadorService.findByEspecialidad(especialidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(trabajadorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TrabajadorResponseDTO> create(@Valid @RequestBody TrabajadorRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trabajadorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabajadorResponseDTO> update(@PathVariable Integer id,
                                                         @Valid @RequestBody TrabajadorRequestDTO request) {
        return ResponseEntity.ok(trabajadorService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TrabajadorResponseDTO> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam Trabajador.EstadoTrabajador estado) {
        return ResponseEntity.ok(trabajadorService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        trabajadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
