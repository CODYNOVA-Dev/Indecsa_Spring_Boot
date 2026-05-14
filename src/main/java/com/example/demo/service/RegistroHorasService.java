package com.example.demo.service;

import com.example.demo.dto.registro.RegistroHorasRequest;
import com.example.demo.dto.registro.RegistroHorasResponse;

import java.time.LocalDate;
import java.util.List;

public interface RegistroHorasService {
    RegistroHorasResponse registrar(RegistroHorasRequest req, Integer idEmpleadoRegistro);
    List<RegistroHorasResponse> listarPorProyecto(Integer idProyecto, LocalDate inicio, LocalDate fin);
    List<RegistroHorasResponse> listarPorTrabajador(Integer idTrabajador);
    RegistroHorasResponse actualizar(Integer id, RegistroHorasRequest req);
    void eliminar(Integer id);
}
