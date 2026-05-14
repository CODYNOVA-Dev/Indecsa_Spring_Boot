package com.example.demo.service;

import com.example.demo.dto.asignacion.AsignacionProyectoContratistaRequest;
import com.example.demo.dto.asignacion.AsignacionProyectoContratistaResponse;

import java.util.List;

public interface AsignacionProyectoContratistaService {
    List<AsignacionProyectoContratistaResponse> findByProyecto(Integer idProyecto);
    List<AsignacionProyectoContratistaResponse> findByContratista(Integer idContratista);
    AsignacionProyectoContratistaResponse findById(Integer id);
    AsignacionProyectoContratistaResponse create(AsignacionProyectoContratistaRequest request);
    AsignacionProyectoContratistaResponse update(Integer id, AsignacionProyectoContratistaRequest request);
    void delete(Integer id);
}
