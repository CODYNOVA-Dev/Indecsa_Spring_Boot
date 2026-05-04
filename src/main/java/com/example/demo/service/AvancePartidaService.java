package com.example.demo.service;

import com.example.demo.dto.avance.AvancePartidaRequest;
import com.example.demo.dto.avance.AvancePartidaResponse;

import java.time.LocalDate;
import java.util.List;

public interface AvancePartidaService {
    AvancePartidaResponse registrar(AvancePartidaRequest req, Integer idEmpleadoRegistro);
    List<AvancePartidaResponse> listarPorProyecto(Integer idProyecto, LocalDate inicio, LocalDate fin);
    AvancePartidaResponse actualizar(Integer id, AvancePartidaRequest req);
    void eliminar(Integer id);
}
