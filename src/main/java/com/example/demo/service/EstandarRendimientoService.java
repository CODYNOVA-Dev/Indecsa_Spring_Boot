package com.example.demo.service;

import com.example.demo.dto.request.EstandarRendimientoRequestDTO;
import com.example.demo.dto.response.EstandarRendimientoResponseDTO;

import java.util.List;

public interface EstandarRendimientoService {
    List<EstandarRendimientoResponseDTO> listarTodos();
    EstandarRendimientoResponseDTO findById(Integer id);
    EstandarRendimientoResponseDTO crear(EstandarRendimientoRequestDTO dto);
    EstandarRendimientoResponseDTO actualizar(Integer id, EstandarRendimientoRequestDTO dto);
    void eliminar(Integer id);
}
