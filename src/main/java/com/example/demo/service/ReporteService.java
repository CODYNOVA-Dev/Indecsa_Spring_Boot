package com.example.demo.service;

import java.time.LocalDate;

public interface ReporteService {
    byte[] generarRendimientoTrabajador(Integer idTrabajador, LocalDate fechaInicio, LocalDate fechaFin);
    byte[] generarHorasProyecto(Integer idProyecto, LocalDate fechaInicio, LocalDate fechaFin);
    byte[] generarAvanceObra(Integer idProyecto, LocalDate fechaInicio, LocalDate fechaFin);
}
