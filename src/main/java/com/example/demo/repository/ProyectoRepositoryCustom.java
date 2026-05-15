package com.example.demo.repository;

import com.example.demo.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProyectoRepositoryCustom {

    Page<Proyecto> findByFiltros(
            String nombre,
            Proyecto.TipoProyecto tipo,
            Proyecto.EstatusProyecto estatus,
            String cliente,
            Pageable pageable
    );
}
