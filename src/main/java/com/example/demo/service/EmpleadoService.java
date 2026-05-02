package com.example.demo.service;

import com.example.demo.dto.empleado.EmpleadoRequest;
import com.example.demo.dto.empleado.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponse> findAll();
    List<EmpleadoResponse> findByRol(Integer idRol);
    EmpleadoResponse findById(Integer id);
    EmpleadoResponse create(EmpleadoRequest request);
    EmpleadoResponse update(Integer id, EmpleadoRequest request);
    void delete(Integer id);
}
