package com.example.demo.service;

import com.example.demo.dto.request.ContratistaRequestDTO;
import com.example.demo.dto.response.ContratistaResponseDTO;
import com.example.demo.model.Contratista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContratistaService {
    Page<ContratistaResponseDTO> findAll(Pageable pageable);
    List<ContratistaResponseDTO> findByEstado(Contratista.EstadoContratista estado);
    ContratistaResponseDTO findById(Integer id);
    ContratistaResponseDTO create(ContratistaRequestDTO request);
    ContratistaResponseDTO update(Integer id, ContratistaRequestDTO request);
    ContratistaResponseDTO cambiarEstado(Integer id, Contratista.EstadoContratista estado);
    void delete(Integer id);
}
