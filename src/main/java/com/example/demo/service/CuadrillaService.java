package com.example.demo.service;

import com.example.demo.dto.request.CuadrillaRequestDTO;
import com.example.demo.dto.response.CuadrillaResponseDTO;
import com.example.demo.model.Cuadrilla.EstatusCuadrilla;

import java.util.List;

public interface CuadrillaService {

    List<CuadrillaResponseDTO> findAll();

    CuadrillaResponseDTO findById(Integer id);

    List<CuadrillaResponseDTO> findByProyecto(Integer idProyecto);

    List<CuadrillaResponseDTO> findByEstatus(EstatusCuadrilla estatus);

    CuadrillaResponseDTO create(CuadrillaRequestDTO dto);

    CuadrillaResponseDTO update(Integer id, CuadrillaRequestDTO dto);

    CuadrillaResponseDTO cambiarEstatus(Integer id, EstatusCuadrilla estatus);

    void delete(Integer id);
}
