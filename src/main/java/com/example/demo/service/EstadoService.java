package com.example.demo.service;

import com.example.demo.dto.response.EstadoResponseDTO;

import java.util.List;

public interface EstadoService {

    List<EstadoResponseDTO> findAll();

    EstadoResponseDTO findById(Integer id);
}
