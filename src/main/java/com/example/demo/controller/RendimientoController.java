package com.example.demo.controller;

import com.example.demo.dto.rendimiento.RendimientoIndicadorResponse;
import com.example.demo.service.RendimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rendimiento")
@RequiredArgsConstructor
public class RendimientoController {

    private final RendimientoService rendimientoService;

    @GetMapping("/proyecto/{idProyecto}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<RendimientoIndicadorResponse>> calcularPorProyecto(
            @PathVariable Integer idProyecto,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(rendimientoService.calcularPorProyecto(idProyecto, fechaInicio, fechaFin));
    }

    @GetMapping("/trabajador/{idTrabajador}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<RendimientoIndicadorResponse>> calcularPorTrabajador(
            @PathVariable Integer idTrabajador,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(rendimientoService.calcularPorTrabajador(idTrabajador, fechaInicio, fechaFin));
    }

    @GetMapping("/cuadrilla/{idCuadrilla}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<RendimientoIndicadorResponse>> calcularPorCuadrilla(
            @PathVariable Integer idCuadrilla,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(rendimientoService.calcularPorCuadrilla(idCuadrilla, fechaInicio, fechaFin));
    }
}
