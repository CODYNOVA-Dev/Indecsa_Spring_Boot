package com.indecsa.repository;

import com.indecsa.model.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrabajadorRepositoryCustom {

    /**
     * Filtrado dinámico de trabajadores.
     * Todos los parámetros son opcionales (null = ignorar filtro).
     */
    Page<Trabajador> findByFiltros(
            String nombre,
            Trabajador.EstadoTrabajador estado,
            String especialidad,
            String puesto,
            Trabajador.EntidadFederativa calidadVida,
            Pageable pageable
    );
}
