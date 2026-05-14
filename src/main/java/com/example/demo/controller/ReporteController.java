package com.example.demo.controller;

import com.example.demo.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    // ─── 1. Rendimiento por Trabajador ──────────────────────────────────────
    @GetMapping(value = "/rendimiento/trabajador/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<byte[]> rendimientoTrabajador(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        byte[] pdf = reporteService.generarRendimientoTrabajador(id, fechaInicio, fechaFin);
        String filename = "rendimiento_trabajador_" + id + "_" + fechaInicio.format(FMT)
                + "_" + fechaFin.format(FMT) + ".pdf";
        return buildResponse(pdf, filename);
    }

    // ─── 2. Horas por Proyecto ──────────────────────────────────────────────
    @GetMapping(value = "/horas/proyecto/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<byte[]> horasProyecto(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        byte[] pdf = reporteService.generarHorasProyecto(id, fechaInicio, fechaFin);
        String filename = "horas_proyecto_" + id + "_" + fechaInicio.format(FMT)
                + "_" + fechaFin.format(FMT) + ".pdf";
        return buildResponse(pdf, filename);
    }

    // ─── 3. Avance de Obra por Proyecto ─────────────────────────────────────
    @GetMapping(value = "/avance/proyecto/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CAPITAL_HUMANO')")
    public ResponseEntity<byte[]> avanceObra(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        byte[] pdf = reporteService.generarAvanceObra(id, fechaInicio, fechaFin);
        String filename = "avance_obra_proyecto_" + id + "_" + fechaInicio.format(FMT)
                + "_" + fechaFin.format(FMT) + ".pdf";
        return buildResponse(pdf, filename);
    }

    // ─── Helper ─────────────────────────────────────────────────────────────
    private ResponseEntity<byte[]> buildResponse(byte[] pdf, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.attachment().filename(filename).build());
        headers.setContentLength(pdf.length);
        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
