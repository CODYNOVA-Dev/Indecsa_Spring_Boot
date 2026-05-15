package com.example.demo.service;

import com.example.demo.dto.request.RegistroHorasRequestDTO;
import com.example.demo.dto.response.RegistroHorasResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface RegistroHorasService {
    RegistroHorasResponseDTO registrar(RegistroHorasRequestDTO request, Integer idEmpleadoRegistro);
    List<RegistroHorasResponseDTO> listarPorProyecto(Integer idProyecto, LocalDate inicio, LocalDate fin);
    List<RegistroHorasResponseDTO> listarPorTrabajador(Integer idTrabajador);
    RegistroHorasResponseDTO actualizar(Integer id, RegistroHorasRequestDTO request);
    void eliminar(Integer id);
}
