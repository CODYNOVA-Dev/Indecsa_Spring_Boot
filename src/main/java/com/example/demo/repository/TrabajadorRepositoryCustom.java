package com.example.demo.repository;

import com.example.demo.model.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrabajadorRepositoryCustom {

    Page<Trabajador> findByFiltros(
            String nombre,
            Trabajador.EstadoTrabajador estado,
            String especialidad,
            String puesto,
            Trabajador.EntidadFederativa calidadVida,
            Pageable pageable
    );
}
