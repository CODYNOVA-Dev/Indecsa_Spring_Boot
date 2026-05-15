package com.example.demo.service;

import com.example.demo.dto.request.TrabajadorRequestDTO;
import com.example.demo.dto.response.TrabajadorResponseDTO;
import com.example.demo.model.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrabajadorService {
    Page<TrabajadorResponseDTO> findAll(Pageable pageable);
    List<TrabajadorResponseDTO> findByEstado(Trabajador.EstadoTrabajador estado);
    List<TrabajadorResponseDTO> findByEspecialidad(String especialidad);
    TrabajadorResponseDTO findById(Integer id);
    TrabajadorResponseDTO create(TrabajadorRequestDTO request);
    TrabajadorResponseDTO update(Integer id, TrabajadorRequestDTO request);
    TrabajadorResponseDTO cambiarEstado(Integer id, Trabajador.EstadoTrabajador estado);
    void delete(Integer id);
}
