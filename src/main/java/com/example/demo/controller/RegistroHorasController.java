package com.example.demo.controller;

import com.example.demo.dto.registro.RegistroHorasRequest;
import com.example.demo.dto.registro.RegistroHorasResponse;
import com.example.demo.model.Empleado;
import com.example.demo.repository.EmpleadoRepository;
import com.example.demo.service.RegistroHorasService;
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
@RequestMapping("/api/registro-horas")
@RequiredArgsConstructor
public class RegistroHorasController {

    private final RegistroHorasService registroHorasService;
    private final EmpleadoRepository empleadoRepository;

    @GetMapping("/proyecto/{idProyecto}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<RegistroHorasResponse>> listarPorProyecto(
            @PathVariable Integer idProyecto,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(registroHorasService.listarPorProyecto(idProyecto, fechaInicio, fechaFin));
    }

    @GetMapping("/trabajador/{idTrabajador}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<RegistroHorasResponse>> listarPorTrabajador(
            @PathVariable Integer idTrabajador) {
        return ResponseEntity.ok(registroHorasService.listarPorTrabajador(idTrabajador));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<RegistroHorasResponse> registrar(@Valid @RequestBody RegistroHorasRequest request) {
        Integer idEmpleado = getEmpleadoAutenticado().getIdEmpleado();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registroHorasService.registrar(request, idEmpleado));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<RegistroHorasResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody RegistroHorasRequest request) {
        return ResponseEntity.ok(registroHorasService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        registroHorasService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private Empleado getEmpleadoAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        return empleadoRepository.findByCorreoEmpleado(correo)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con correo: " + correo));
    }
}
