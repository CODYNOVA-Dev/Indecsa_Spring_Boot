package com.example.demo.service;

import com.example.demo.dto.request.EstadoRequestDTO;
import com.example.demo.dto.response.EstadoResponseDTO;

import java.util.List;

public interface EstadoService {

    List<EstadoResponseDTO> findAll();

    EstadoResponseDTO findById(Integer id);

    EstadoResponseDTO create(EstadoRequestDTO dto);

    EstadoResponseDTO update(Integer id, EstadoRequestDTO dto);

    void delete(Integer id);
}
