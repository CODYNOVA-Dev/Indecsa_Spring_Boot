package com.example.demo.service;

import com.example.demo.dto.request.CuadrillaRequestDTO;
import com.example.demo.dto.response.CuadrillaResponseDTO;

import java.util.List;

public interface CuadrillaService {
    List<CuadrillaResponseDTO> listarPorProyecto(Integer idProyecto);
    CuadrillaResponseDTO crear(CuadrillaRequestDTO dto);
    CuadrillaResponseDTO actualizar(Integer id, CuadrillaRequestDTO dto);
    void eliminar(Integer id);
}
