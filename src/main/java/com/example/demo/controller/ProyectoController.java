package com.example.demo.controller;

import com.example.demo.dto.request.ProyectoRequestDTO;
import com.example.demo.dto.response.ProyectoResponseDTO;
import com.example.demo.model.Proyecto.EstatusProyecto;
import com.example.demo.model.Proyecto.TipoProyecto;
import com.example.demo.service.ProyectoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ProyectoResponseDTO>> findAll() {
        return ResponseEntity.ok(proyectoService.findAll());
    }

    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<ProyectoResponseDTO>> findByEstatus(
            @PathVariable Proyecto.EstatusProyecto estatus) {
        return ResponseEntity.ok(proyectoService.findByEstatus(estatus));
    }

    // ─── GET BY TIPO ──────────────────────────────────────────────────────────
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ProyectoResponseDTO>> getByTipo(@PathVariable TipoProyecto tipo) {
        return ResponseEntity.ok(proyectoService.findByTipo(tipo));
    }

    // ─── GET BY CLIENTE ───────────────────────────────────────────────────────
    @GetMapping("/cliente/{cliente}")
    public ResponseEntity<List<ProyectoResponseDTO>> getByCliente(@PathVariable String cliente) {
        return ResponseEntity.ok(proyectoService.findByCliente(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(proyectoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProyectoResponseDTO> create(@Valid @RequestBody ProyectoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponseDTO> update(@PathVariable Integer id,
                                                       @Valid @RequestBody ProyectoRequestDTO request) {
        return ResponseEntity.ok(proyectoService.update(id, request));
    }

    @PatchMapping("/{id}/estatus")
    public ResponseEntity<ProyectoResponseDTO> cambiarEstatus(
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
