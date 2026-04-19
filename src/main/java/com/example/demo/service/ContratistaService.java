package com.indecsa.service;

import com.indecsa.dto.contratista.ContratistaRequest;
import com.indecsa.dto.contratista.ContratistaResponse;
import com.indecsa.model.Contratista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContratistaService {
    Page<ContratistaResponse> findByFiltros(
            String nombre,
            Contratista.EstadoContratista estado,
            Contratista.EntidadFederativa ubicacion,
            Byte calificacionMin,
            Pageable pageable
    );
    ContratistaResponse findById(Integer id);
    ContratistaResponse create(ContratistaRequest request);
    ContratistaResponse update(Integer id, ContratistaRequest request);
    void delete(Integer id);
}
