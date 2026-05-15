package com.example.demo.service;

import com.example.demo.dto.request.AsignacionProyectoContratistaRequestDTO;
import com.example.demo.dto.response.AsignacionProyectoContratistaResponseDTO;

import java.util.List;

public interface AsignacionProyectoContratistaService {
    List<AsignacionProyectoContratistaResponseDTO> findByProyecto(Integer idProyecto);
    List<AsignacionProyectoContratistaResponseDTO> findByContratista(Integer idContratista);
    AsignacionProyectoContratistaResponseDTO findById(Integer id);
    AsignacionProyectoContratistaResponseDTO create(AsignacionProyectoContratistaRequestDTO request);
    AsignacionProyectoContratistaResponseDTO update(Integer id, AsignacionProyectoContratistaRequestDTO request);
    void delete(Integer id);
}
