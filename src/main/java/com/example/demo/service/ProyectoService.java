package com.example.demo.service;

import com.example.demo.dto.request.ProyectoRequestDTO;
import com.example.demo.dto.response.ProyectoResponseDTO;
import com.example.demo.model.Proyecto;

import java.util.List;

public interface ProyectoService {
    List<ProyectoResponseDTO> findAll();
    List<ProyectoResponseDTO> findByEstatus(Proyecto.EstatusProyecto estatus);
    List<ProyectoResponseDTO> findByMunicipio(String municipio);
    ProyectoResponseDTO findById(Integer id);
    ProyectoResponseDTO create(ProyectoRequestDTO request);
    ProyectoResponseDTO update(Integer id, ProyectoRequestDTO request);
    ProyectoResponseDTO cambiarEstatus(Integer id, Proyecto.EstatusProyecto estatus);
    void delete(Integer id);
}
