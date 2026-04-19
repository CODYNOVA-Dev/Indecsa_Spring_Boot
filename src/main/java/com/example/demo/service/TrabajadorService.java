package com.indecsa.service;

import com.indecsa.dto.trabajador.TrabajadorRequest;
import com.indecsa.dto.trabajador.TrabajadorResponse;
import com.indecsa.model.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrabajadorService {
    Page<TrabajadorResponse> findByFiltros(
            String nombre,
            Trabajador.EstadoTrabajador estado,
            String especialidad,
            String puesto,
            Trabajador.EntidadFederativa calidadVida,
            Pageable pageable
    );
    TrabajadorResponse findById(Integer id);
    TrabajadorResponse create(TrabajadorRequest request);
    TrabajadorResponse update(Integer id, TrabajadorRequest request);
    void delete(Integer id);
}
