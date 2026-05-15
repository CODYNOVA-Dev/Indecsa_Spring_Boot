package com.example.demo.service;

import com.example.demo.dto.request.CuadrillaRequestDTO;
import com.example.demo.dto.response.CuadrillaResponseDTO;

import java.util.List;

public interface CuadrillaService {
    CuadrillaResponseDTO crear(CuadrillaRequestDTO request);
    List<CuadrillaResponseDTO> listarPorProyecto(Integer idProyecto);
    CuadrillaResponseDTO actualizar(Integer id, CuadrillaRequestDTO request);
    void eliminar(Integer id);
}
