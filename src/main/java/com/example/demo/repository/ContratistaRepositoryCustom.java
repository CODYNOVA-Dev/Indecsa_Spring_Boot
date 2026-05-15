package com.example.demo.repository;

import com.example.demo.model.Contratista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContratistaRepositoryCustom {

    Page<Contratista> findByFiltros(
            String nombre,
            Contratista.EstadoContratista estado,
            Byte calificacionMin,
            Pageable pageable
    );
}
