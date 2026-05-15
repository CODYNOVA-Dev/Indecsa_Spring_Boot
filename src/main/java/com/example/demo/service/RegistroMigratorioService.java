package com.example.demo.service;

import com.example.demo.dto.request.RegistroMigratorioRequestDTO;
import com.example.demo.dto.response.RegistroMigratorioResponseDTO;

import java.util.List;

public interface RegistroMigratorioService {

    List<RegistroMigratorioResponseDTO> findAll();

    RegistroMigratorioResponseDTO findById(Integer id);

    RegistroMigratorioResponseDTO create(RegistroMigratorioRequestDTO dto);

    RegistroMigratorioResponseDTO update(Integer id, RegistroMigratorioRequestDTO dto);

    RegistroMigratorioResponseDTO cambiarActivo(Integer id, Boolean activo);

    void delete(Integer id);
}
