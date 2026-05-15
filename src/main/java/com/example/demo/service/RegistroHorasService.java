package com.example.demo.service;

import com.example.demo.dto.request.RegistroHorasRequestDTO;
import com.example.demo.dto.response.RegistroHorasResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface RegistroHorasService {

    List<RegistroHorasResponseDTO> findAll();

    RegistroHorasResponseDTO findById(Integer id);

    List<RegistroHorasResponseDTO> findByAsignacion(Integer idAsignacionTp);

    List<RegistroHorasResponseDTO> findByCuadrilla(Integer idCuadrilla);

    List<RegistroHorasResponseDTO> findByFecha(LocalDate fecha);

    RegistroHorasResponseDTO create(RegistroHorasRequestDTO dto);

    RegistroHorasResponseDTO update(Integer id, RegistroHorasRequestDTO dto);

    void delete(Integer id);
}
