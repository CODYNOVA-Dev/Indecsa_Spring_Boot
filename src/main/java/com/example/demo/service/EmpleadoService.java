package com.indecsa.service;

import com.indecsa.dto.empleado.EmpleadoRequest;
import com.indecsa.dto.empleado.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponse> findAll();
    EmpleadoResponse findById(Integer id);
    EmpleadoResponse create(EmpleadoRequest request);
    EmpleadoResponse update(Integer id, EmpleadoRequest request);
    void delete(Integer id);
}
