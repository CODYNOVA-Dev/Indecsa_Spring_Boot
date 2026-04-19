package com.indecsa.controller;

import com.indecsa.dto.migratorio.RegistroMigratorioRequest;
import com.indecsa.dto.migratorio.RegistroMigratorioResponse;
import com.indecsa.service.RegistroMigratorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros-migratorios")
@RequiredArgsConstructor
public class RegistroMigratorioController {

    private final RegistroMigratorioService migratorioService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<List<RegistroMigratorioResponse>> findAll() {
        return ResponseEntity.ok(migratorioService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<RegistroMigratorioResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(migratorioService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<RegistroMigratorioResponse> create(
            @Valid @RequestBody RegistroMigratorioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(migratorioService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<RegistroMigratorioResponse> update(@PathVariable Integer id,
                                                              @Valid @RequestBody RegistroMigratorioRequest request) {
        return ResponseEntity.ok(migratorioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        migratorioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
