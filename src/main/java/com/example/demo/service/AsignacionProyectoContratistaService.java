package com.indecsa.service;

import com.indecsa.dto.asignacion.AsignacionProyectoContratistaRequest;
import com.indecsa.dto.asignacion.AsignacionProyectoContratistaResponse;

import java.util.List;

public interface AsignacionProyectoContratistaService {
    List<AsignacionProyectoContratistaResponse> findByProyecto(Integer idProyecto);
    List<AsignacionProyectoContratistaResponse> findByContratista(Integer idContratista);
    AsignacionProyectoContratistaResponse findById(Integer id);
    AsignacionProyectoContratistaResponse create(AsignacionProyectoContratistaRequest request);
    AsignacionProyectoContratistaResponse update(Integer id, AsignacionProyectoContratistaRequest request);
    void delete(Integer id);
}
