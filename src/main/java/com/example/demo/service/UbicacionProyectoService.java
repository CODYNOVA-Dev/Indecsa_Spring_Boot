package com.indecsa.service;

import com.indecsa.dto.ubicacion.UbicacionProyectoRequest;
import com.indecsa.dto.ubicacion.UbicacionProyectoResponse;
import com.indecsa.model.UbicacionProyecto;

import java.util.List;

public interface UbicacionProyectoService {
    List<UbicacionProyectoResponse> findAll();
    List<UbicacionProyectoResponse> findByEstado(UbicacionProyecto.EntidadFederativa estado);
    UbicacionProyectoResponse findById(Integer id);
    UbicacionProyectoResponse create(UbicacionProyectoRequest request);
    UbicacionProyectoResponse update(Integer id, UbicacionProyectoRequest request);
    void delete(Integer id);
}
