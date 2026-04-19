package com.indecsa.repository;

import com.indecsa.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProyectoRepositoryCustom {

    /**
     * Filtrado dinámico de proyectos.
     * Todos los parámetros son opcionales (null = ignorar filtro).
     */
    Page<Proyecto> findByFiltros(
            String nombre,
            Proyecto.TipoProyecto tipo,
            Proyecto.EstatusProyecto estatus,
            Proyecto.EntidadFederativa estadoGeo,
            String cliente,
            Pageable pageable
    );
}
