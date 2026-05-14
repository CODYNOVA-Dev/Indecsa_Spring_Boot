package com.example.demo.service;

import com.example.demo.dto.request.AsignacionTrabajadorProyectoRequestDTO;
import com.example.demo.dto.response.AsignacionTrabajadorProyectoResponseDTO;

import java.util.List;

public interface AsignacionTrabajadorProyectoService {
    List<AsignacionTrabajadorProyectoResponseDTO> findByProyecto(Integer idProyecto);
    List<AsignacionTrabajadorProyectoResponseDTO> findByTrabajador(Integer idTrabajador);
    AsignacionTrabajadorProyectoResponseDTO findById(Integer id);
    AsignacionTrabajadorProyectoResponseDTO create(AsignacionTrabajadorProyectoRequestDTO dto);
    AsignacionTrabajadorProyectoResponseDTO update(Integer id, AsignacionTrabajadorProyectoRequestDTO dto);
    void delete(Integer id);
}
