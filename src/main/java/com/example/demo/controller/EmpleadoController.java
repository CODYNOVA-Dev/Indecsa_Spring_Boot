package com.example.demo.controller;

import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.LoginResponse;
import com.example.demo.dto.request.EmpleadoRequestDTO;
import com.example.demo.dto.response.EmpleadoResponseDTO;
import com.example.demo.service.AuthService;
import com.example.demo.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> findAll() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<EmpleadoResponseDTO>> findByRol(@PathVariable Integer idRol) {
        return ResponseEntity.ok(empleadoService.findByRol(idRol));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(empleadoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> create(@Valid @RequestBody EmpleadoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> update(@PathVariable Integer id,
                                                       @Valid @RequestBody EmpleadoRequestDTO request) {
        return ResponseEntity.ok(empleadoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
