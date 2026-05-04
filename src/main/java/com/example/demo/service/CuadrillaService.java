package com.example.demo.service;

import com.example.demo.dto.cuadrilla.CuadrillaRequest;
import com.example.demo.dto.cuadrilla.CuadrillaResponse;

import java.util.List;

public interface CuadrillaService {
    CuadrillaResponse crear(CuadrillaRequest req);
    List<CuadrillaResponse> listarPorProyecto(Integer idProyecto);
    CuadrillaResponse actualizar(Integer id, CuadrillaRequest req);
    void eliminar(Integer id);
}
