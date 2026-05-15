package com.example.demo.service;

import com.example.demo.dto.request.AvancePartidaRequestDTO;
import com.example.demo.dto.response.AvancePartidaResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface AvancePartidaService {

    List<AvancePartidaResponseDTO> findAll();

    AvancePartidaResponseDTO findById(Integer id);

    List<AvancePartidaResponseDTO> findByProyecto(Integer idProyecto);

    List<AvancePartidaResponseDTO> findByCuadrilla(Integer idCuadrilla);

    List<AvancePartidaResponseDTO> findByFecha(LocalDate fecha);

    AvancePartidaResponseDTO create(AvancePartidaRequestDTO dto);

    AvancePartidaResponseDTO update(Integer id, AvancePartidaRequestDTO dto);

    void delete(Integer id);
}
