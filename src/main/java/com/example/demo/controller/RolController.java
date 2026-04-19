package com.indecsa.controller;

import com.indecsa.dto.rol.RolRequest;
import com.indecsa.dto.rol.RolResponse;
import com.indecsa.service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RolResponse>> findAll() {
        return ResponseEntity.ok(rolService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(rolService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolResponse> create(@Valid @RequestBody RolRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolResponse> update(@PathVariable Integer id,
                                               @Valid @RequestBody RolRequest request) {
        return ResponseEntity.ok(rolService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
