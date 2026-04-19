package com.indecsa.service;

import com.indecsa.dto.proyecto.ProyectoRequest;
import com.indecsa.dto.proyecto.ProyectoResponse;
import com.indecsa.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProyectoService {
    Page<ProyectoResponse> findByFiltros(
            String nombre,
            Proyecto.TipoProyecto tipo,
            Proyecto.EstatusProyecto estatus,
            Proyecto.EntidadFederativa estadoGeo,
            String cliente,
            Pageable pageable
    );
    ProyectoResponse findById(Integer id);
    ProyectoResponse create(ProyectoRequest request);
    ProyectoResponse update(Integer id, ProyectoRequest request);
    void delete(Integer id);
}
