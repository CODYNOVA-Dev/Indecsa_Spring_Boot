package com.indecsa.service;

import com.indecsa.dto.asignacion.AsignacionTrabajadorProyectoRequest;
import com.indecsa.dto.asignacion.AsignacionTrabajadorProyectoResponse;

import java.util.List;

public interface AsignacionTrabajadorProyectoService {
    List<AsignacionTrabajadorProyectoResponse> findByProyecto(Integer idProyecto);
    List<AsignacionTrabajadorProyectoResponse> findByTrabajador(Integer idTrabajador);
    AsignacionTrabajadorProyectoResponse findById(Integer id);
    AsignacionTrabajadorProyectoResponse create(AsignacionTrabajadorProyectoRequest request);
    AsignacionTrabajadorProyectoResponse update(Integer id, AsignacionTrabajadorProyectoRequest request);
    void delete(Integer id);
}
