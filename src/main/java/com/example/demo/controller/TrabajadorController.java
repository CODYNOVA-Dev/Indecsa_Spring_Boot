package com.example.demo.controller;

import com.example.demo.dto.trabajador.TrabajadorRequest;
import com.example.demo.dto.trabajador.TrabajadorResponse;
import com.example.demo.model.Trabajador;
import com.example.demo.service.TrabajadorService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<TrabajadorResponse>> findAll() {
        return ResponseEntity.ok(trabajadorService.findAll());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TrabajadorResponse>> findByEstado(
            @PathVariable Trabajador.EstadoTrabajador estado) {
        return ResponseEntity.ok(trabajadorService.findByEstado(estado));
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<TrabajadorResponse>> findByEspecialidad(
            @PathVariable String especialidad) {
        return ResponseEntity.ok(trabajadorService.findByEspecialidad(especialidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(trabajadorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TrabajadorResponse> create(@RequestBody TrabajadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trabajadorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabajadorResponse> update(@PathVariable Integer id,
                                                      @RequestBody TrabajadorRequest request) {
        return ResponseEntity.ok(trabajadorService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TrabajadorResponse> cambiarEstado(
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
