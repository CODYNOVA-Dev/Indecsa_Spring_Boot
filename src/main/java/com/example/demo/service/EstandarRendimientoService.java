package com.example.demo.service;

import com.example.demo.dto.request.EstandarRendimientoRequestDTO;
import com.example.demo.dto.response.EstandarRendimientoResponseDTO;
import com.example.demo.model.EstandarRendimiento.UnidadMedida;

import java.util.List;

public interface EstandarRendimientoService {

    List<EstandarRendimientoResponseDTO> findAll();

    EstandarRendimientoResponseDTO findById(Integer id);

    List<EstandarRendimientoResponseDTO> findByNombreActividad(String nombre);

    List<EstandarRendimientoResponseDTO> findByUnidadMedida(UnidadMedida unidadMedida);

    EstandarRendimientoResponseDTO create(EstandarRendimientoRequestDTO dto);

    EstandarRendimientoResponseDTO update(Integer id, EstandarRendimientoRequestDTO dto);

    void delete(Integer id);
}
