package com.example.demo.service;

import com.example.demo.dto.request.TrabajadorRequestDTO;
import com.example.demo.dto.response.TrabajadorResponseDTO;
import com.example.demo.model.Trabajador.EstadoTrabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrabajadorService {
    Page<TrabajadorResponseDTO> findAll(Pageable pageable);
    List<TrabajadorResponseDTO> findByEstado(EstadoTrabajador estado);
    List<TrabajadorResponseDTO> findByEspecialidad(String especialidad);
    TrabajadorResponseDTO findById(Integer id);
    TrabajadorResponseDTO create(TrabajadorRequestDTO dto);
    TrabajadorResponseDTO update(Integer id, TrabajadorRequestDTO dto);
    TrabajadorResponseDTO cambiarEstado(Integer id, EstadoTrabajador estado);
    void delete(Integer id);
}
