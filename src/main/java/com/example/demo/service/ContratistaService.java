package com.example.demo.service;

import com.example.demo.dto.contratista.ContratistaRequest;
import com.example.demo.dto.contratista.ContratistaResponse;
import com.example.demo.model.Contratista;

import java.util.List;

public interface ContratistaService {
    List<ContratistaResponse> findAll();
    List<ContratistaResponse> findByEstado(Contratista.EstadoContratista estado);
    ContratistaResponse findById(Integer id);
    ContratistaResponse create(ContratistaRequest request);
    ContratistaResponse update(Integer id, ContratistaRequest request);
    ContratistaResponse cambiarEstado(Integer id, Contratista.EstadoContratista estado);
    void delete(Integer id);
}
