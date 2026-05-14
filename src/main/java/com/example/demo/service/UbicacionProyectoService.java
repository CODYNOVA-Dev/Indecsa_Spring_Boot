package com.example.demo.service;

import com.example.demo.dto.ubicacion.UbicacionProyectoRequest;
import com.example.demo.dto.ubicacion.UbicacionProyectoResponse;
import com.example.demo.model.UbicacionProyecto;

import java.util.List;

public interface UbicacionProyectoService {
    List<UbicacionProyectoResponse> findAll();
    List<UbicacionProyectoResponse> findByEstado(UbicacionProyecto.EntidadFederativa estado);
    UbicacionProyectoResponse findById(Integer id);
    UbicacionProyectoResponse create(UbicacionProyectoRequest request);
    UbicacionProyectoResponse update(Integer id, UbicacionProyectoRequest request);
    void delete(Integer id);
}
