package com.example.demo.service;

import com.example.demo.dto.trabajador.TrabajadorRequest;
import com.example.demo.dto.trabajador.TrabajadorResponse;
import com.example.demo.model.Trabajador;

import java.util.List;

public interface TrabajadorService {
    List<TrabajadorResponse> findAll();
    List<TrabajadorResponse> findByEstado(Trabajador.EstadoTrabajador estado);
    List<TrabajadorResponse> findByEspecialidad(String especialidad);
    TrabajadorResponse findById(Integer id);
    TrabajadorResponse create(TrabajadorRequest request);
    TrabajadorResponse update(Integer id, TrabajadorRequest request);
    TrabajadorResponse cambiarEstado(Integer id, Trabajador.EstadoTrabajador estado);
    void delete(Integer id);
}
