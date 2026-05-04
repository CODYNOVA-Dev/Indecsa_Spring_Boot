package com.example.demo.service;

import com.example.demo.dto.rendimiento.RendimientoIndicadorResponse;

import java.time.LocalDate;
import java.util.List;

public interface RendimientoService {
    List<RendimientoIndicadorResponse> calcularPorProyecto(Integer idProyecto, LocalDate inicio, LocalDate fin);
    List<RendimientoIndicadorResponse> calcularPorTrabajador(Integer idTrabajador, LocalDate inicio, LocalDate fin);
    List<RendimientoIndicadorResponse> calcularPorCuadrilla(Integer idCuadrilla, LocalDate inicio, LocalDate fin);
}
