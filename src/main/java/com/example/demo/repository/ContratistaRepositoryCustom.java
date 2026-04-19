package com.indecsa.repository;

import com.indecsa.model.Contratista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContratistaRepositoryCustom {

    /**
     * Filtrado dinámico de contratistas.
     * Todos los parámetros son opcionales (null = ignorar filtro).
     */
    Page<Contratista> findByFiltros(
            String nombre,
            Contratista.EstadoContratista estado,
            Contratista.EntidadFederativa ubicacion,
            Byte calificacionMin,
            Pageable pageable
    );
}
