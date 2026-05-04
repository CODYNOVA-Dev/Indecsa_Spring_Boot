package com.example.demo.service;

import com.example.demo.dto.estandar.EstandarRendimientoRequest;
import com.example.demo.dto.estandar.EstandarRendimientoResponse;

import java.util.List;

public interface EstandarRendimientoService {
    EstandarRendimientoResponse crear(EstandarRendimientoRequest req);
    List<EstandarRendimientoResponse> listarTodos();
    EstandarRendimientoResponse findById(Integer id);
    EstandarRendimientoResponse actualizar(Integer id, EstandarRendimientoRequest req);
    void eliminar(Integer id);
}
