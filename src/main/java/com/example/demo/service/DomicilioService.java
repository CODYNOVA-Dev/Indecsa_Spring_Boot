package com.example.demo.service;

import com.example.demo.dto.request.DomicilioRequestDTO;
import com.example.demo.dto.response.DomicilioResponseDTO;

import java.util.List;

public interface DomicilioService {

    List<DomicilioResponseDTO> findAll();

    DomicilioResponseDTO findById(Integer id);

    DomicilioResponseDTO create(DomicilioRequestDTO dto);

    DomicilioResponseDTO update(Integer id, DomicilioRequestDTO dto);

    void delete(Integer id);
}
