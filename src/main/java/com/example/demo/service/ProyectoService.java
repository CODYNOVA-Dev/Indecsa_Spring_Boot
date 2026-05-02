package com.example.demo.service;

import com.example.demo.dto.proyecto.ProyectoRequest;
import com.example.demo.dto.proyecto.ProyectoResponse;
import com.example.demo.model.Proyecto;

import java.util.List;

public interface ProyectoService {
    List<ProyectoResponse> findAll();
    List<ProyectoResponse> findByEstatus(Proyecto.EstatusProyecto estatus);
    List<ProyectoResponse> findByMunicipio(String municipio);
    ProyectoResponse findById(Integer id);
    ProyectoResponse create(ProyectoRequest request);
    ProyectoResponse update(Integer id, ProyectoRequest request);
    ProyectoResponse cambiarEstatus(Integer id, Proyecto.EstatusProyecto estatus);
    void delete(Integer id);
}
