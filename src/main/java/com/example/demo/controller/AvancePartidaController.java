package com.example.demo.controller;

import com.example.demo.dto.avance.AvancePartidaRequest;
import com.example.demo.dto.avance.AvancePartidaResponse;
import com.example.demo.model.Empleado;
import com.example.demo.repository.EmpleadoRepository;
import com.example.demo.service.AvancePartidaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/avance-partida")
@RequiredArgsConstructor
public class AvancePartidaController {

    private final AvancePartidaService avancePartidaService;
    private final EmpleadoRepository empleadoRepository;

    @GetMapping("/proyecto/{idProyecto}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<AvancePartidaResponse>> listarPorProyecto(
            @PathVariable Integer idProyecto,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(avancePartidaService.listarPorProyecto(idProyecto, fechaInicio, fechaFin));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AvancePartidaResponse> registrar(@RequestBody AvancePartidaRequest request) {
        Integer idEmpleado = getEmpleadoAutenticado().getIdEmpleado();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(avancePartidaService.registrar(request, idEmpleado));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<AvancePartidaResponse> actualizar(
            @PathVariable Integer id,
            @RequestBody AvancePartidaRequest request) {
        return ResponseEntity.ok(avancePartidaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        avancePartidaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private Empleado getEmpleadoAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        return empleadoRepository.findByCorreoEmpleado(correo)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con correo: " + correo));
    }
}
