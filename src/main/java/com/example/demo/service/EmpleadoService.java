package com.example.demo.service;

import com.example.demo.dto.request.EmpleadoRequestDTO;
import com.example.demo.dto.response.EmpleadoResponseDTO;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponseDTO> findAll();
    List<EmpleadoResponseDTO> findByRol(Integer idRol);
    EmpleadoResponseDTO findById(Integer id);
    EmpleadoResponseDTO create(EmpleadoRequestDTO dto);
    EmpleadoResponseDTO update(Integer id, EmpleadoRequestDTO dto);
    void delete(Integer id);
}
