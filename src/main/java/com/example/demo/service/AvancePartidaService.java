package com.example.demo.service;

import com.example.demo.dto.request.AvancePartidaRequestDTO;
import com.example.demo.dto.response.AvancePartidaResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface AvancePartidaService {
    AvancePartidaResponseDTO registrar(AvancePartidaRequestDTO dto, Integer idEmpleadoRegistro);
    List<AvancePartidaResponseDTO> listarPorProyecto(Integer idProyecto, LocalDate inicio, LocalDate fin);
    AvancePartidaResponseDTO actualizar(Integer id, AvancePartidaRequestDTO dto);
    void eliminar(Integer id);
}
