package com.example.demo.controller;

import com.example.demo.dto.proyecto.ProyectoRequest;
import com.example.demo.dto.proyecto.ProyectoResponse;
import com.example.demo.model.Proyecto;
import com.example.demo.service.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<ProyectoResponse>> findAll() {
        return ResponseEntity.ok(proyectoService.findAll());
    }

    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<ProyectoResponse>> findByEstatus(
            @PathVariable Proyecto.EstatusProyecto estatus) {
        return ResponseEntity.ok(proyectoService.findByEstatus(estatus));
    }

    @GetMapping("/municipio/{municipio}")
    public ResponseEntity<List<ProyectoResponse>> findByMunicipio(
            @PathVariable String municipio) {
        return ResponseEntity.ok(proyectoService.findByMunicipio(municipio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(proyectoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProyectoResponse> create(@RequestBody ProyectoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponse> update(@PathVariable Integer id,
                                                    @RequestBody ProyectoRequest request) {
        return ResponseEntity.ok(proyectoService.update(id, request));
    }

    @PatchMapping("/{id}/estatus")
    public ResponseEntity<ProyectoResponse> cambiarEstatus(
            @PathVariable Integer id,
            @RequestParam Proyecto.EstatusProyecto estatus) {
        return ResponseEntity.ok(proyectoService.cambiarEstatus(id, estatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        proyectoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
