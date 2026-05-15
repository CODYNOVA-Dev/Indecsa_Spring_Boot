package com.example.demo.service;

import com.example.demo.dto.request.EstandarRendimientoRequestDTO;
import com.example.demo.dto.response.EstandarRendimientoResponseDTO;

import java.util.List;

public interface EstandarRendimientoService {
    EstandarRendimientoResponseDTO crear(EstandarRendimientoRequestDTO request);
    List<EstandarRendimientoResponseDTO> listarTodos();
    EstandarRendimientoResponseDTO findById(Integer id);
    EstandarRendimientoResponseDTO actualizar(Integer id, EstandarRendimientoRequestDTO request);
    void eliminar(Integer id);
}
